var $btn = $('#ctlBtn');
var $pursebtn = $('#purseBtn');
var $thelist = $('#thelist');
// 此处的大小和后端约定，保持一致
var chunkSize = 5 * 1024 * 1024;

// 添加组件
WebUploader.Uploader.register({
    'before-send-file': 'beforeSendFile',
    'before-send': 'beforeSend'
},{
    beforeSendFile: function (file) {
        // 上传文件前先计算文件的md5值，然后根据MD5来判断服务端是否已经传了当前文件
        // 如果已经上传，则不用再次上传，只需要告诉传输的结果即可
        console.log("校验文件的md5值");
        // Deferred对象在钩子回掉函数中经常要用到，用来处理需要等待的异步操作。
        var task = new $.Deferred();
        // 根据文件内容来查询MD5
        uploader.md5File(file).progress(function (percentage) {   // 及时显示进度
            console.log('计算md5进度:', percentage);
            percentage = percentage.toFixed(2);
            // 生成md5进度条
            getProgressBar(file, percentage, "MD5", "MD5");
        }).then(function (val) { // MD5计算完成
            console.log("计算md5完成，md5值：" + val);
            file.md5 = val;
            // 模拟用户id
            file.uid = WebUploader.Base.guid();
            // 进行md5判断
            $.post("file/checkFileMd5", {uid : file.uid, md5 : file.md5},
                function (data) {
                    console.log(data.result);
                    var status = data.result.code;
                    task.resolve();
                    if (status == 101) {
                        // 文件不存在，则进行正常流程
                    } else if(status == 100) {
                        // 文件已经存在，则直接跳过
                        uploader.skipFile(file);
                        file.pass = true;
                    } else if (status == 102) {
                        // 已经上传一部分，继续上传
                        file.missChunks = data.data;
                    }
            });
        });
        return $.when(task);
    },
    beforeSend: function (block) {
        // 将文件分块
        console.log("检测分块文件是否已经上传");
        var task = $.Deferred();
        var file = block.file;
        // 遗失的分块
        var missChunks = block.missChunks;
        // 当前分块
        var chunk = block.chunk;
        // 当前块的标志， true 表示已经上传  false 表示未上传
        var flag = true;
        if (missChunks !== null && missChunks !== undefined && missChunks !== '') {
            for (var i = 0; i < missChunks.length; i++) {
                if (chunk == missChunks[i]) {
                    console.log("文件" + file.name + ':' + chunk + "没有上传，现在开始上传");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                task.resolve(); // 将task对象的执行状态从"未完成"改为"已完成"
            } else {
                task.reject();   // 将task对象的执行状态从"未完成"改为"已失败"
            }
        } else {
            task.resolve();
        }
        return $.when(task);
    }
    
});

// 实例化对象
var uploader = WebUploader.create({
    //dnd: '#dndArea',
    //paste: '#uploader',
    // 指定选择文件的按钮容器，不指定则不创建按钮。
    pick: {
        id:'#getFile', // 指定选择文件的按钮容器，不指定则不创建按钮。注意 这里虽然写的是 id, 但是不是只支持 id, 还支持 class, 或者 dom 节点
        label: '选择文件', // 请采用 innerHTML 代替
        multiple: true   // 是否开起同时选择多个文件能力
    },
    auto: false , // 设置为 true 后，不需要手动调用上传，有文件选择即开始上传
    prepareNextFile: true, //否允许在文件传输时提前把下一个文件准备好。 某些文件的准备工作比较耗时，比如图片压缩，md5序列化。 如果能提前在当前文件传输期处理，可以节省总体耗时。
    chunked: true, // 是否要分片处理大文件上传
    chunkSize: chunkSize,  // 如果要分片，分多大一片？ 默认大小为5M
    chunkRetry: 4, //如果某个分片由于网络问题出错，允许自动重传多少次
    chunkRetryDelay: 1000, // 开启重试后，设置重试延时时间, 单位毫秒
    threads: 3, // 上传并发数。允许同时最大上传进程数
    formData: {
        uid: 0,
        md5: '',
        chunkSize: chunkSize
    },
    server: 'file/fileUpload',
    swf: 'js/Uploader.swf',
    fileNumLimit: 1000, //验证文件总数量, 超出则不允许加入队列
    fileSizeLimit: 1024 * 1024 * 1024 * 1024, // 10G,验证文件总大小是否超出限制, 超出则不允许加入队列
    fileSingleSizeLimit: 1024 * 1024 * 1024 * 10,// 验证单个文件大小是否超出限制, 超出则不允许加入队列
    duplicate: false, //去重， 根据文件名字、文件大小和最后修改时间来生成hash Key
});

// 事件说明
// beforeFileQueued  当文件被加入队列之前触发。如果此事件handler的返回值为false，则此文件不会被添加进入队列。

// 当文件被加入队列以后触发
// uploader.on('fileQueued', function (file) {
//     console.log("fileQueued");
//     $thelist.append('<div id="' + file.id + '" class="item">' +
//         '<h4 class="info">' + file.name + '</h4>' +
//         '<p class="state">等待上传...</p>' +
//         '</div>');
// });

// 当一批文件添加进队列以后触发
uploader.on('filesQueued', function (files) {
    console.log("filesQueued");
    for (var i = 0; i < files.length; i++) {
        $thelist.append('<div id="' + files[i].id + '" class="item">' +
            '<h4 class="info">' + files[i].name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>');
    }
});
// 当文件被移除队列后触发
uploader.on('filesQueued', function (files) {

});



//当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
uploader.onUploadBeforeSend = function (obj, data) {
    console.log("onUploadBeforeSend");
    var file = obj.file;
    data.md5 = file.md5 || '';
    data.uid = file.uid;
};

// 上传过程中触发，携带上传进度
uploader.on('uploadProgress', function (file, percentage) {
    percentage = percentage.toFixed(2);
    getProgressBar(file, percentage, "FILE", "上传进度");
});

// 当文件上传成功时触发
uploader.on('uploadSuccess', function (file) {
    var text = '文件已上传';
    if (file.pass) {
        text = "文件秒传功能，文件已上传。"
    }
    $('#' + file.id).find('p.state').text(text);
});

// 当文件上传出错时触发
uploader.on('uploadError', function (file) {
    $('#' + file.id).find('p.state').text('上传出错');
});

// 不管成功或者失败，文件上传完成时触发
uploader.on('uploadComplete', function (file) {
    // 隐藏进度条
    // fadeOutProgress(file, 'MD5');
    // fadeOutProgress(file, 'FILE');
});

// 文件上传
$btn.on('click', function () {
    console.log("上传...");
    uploader.upload();
    console.log("上传成功");
});

// 暂停上传
$pursebtn.on('click', function () {
    console.log("暂停上传")
    uploader.stop(true);
});

/**
 *  生成进度条封装方法
 * @param file 文件
 * @param percentage 进度值
 * @param id_Prefix id前缀
 * @param titleName 标题名
 */
function getProgressBar(file, percentage, id_Prefix, titleName) {
    var $li = $('#' + file.id), $percent = $li.find('#' + id_Prefix + '-progress-bar');
    // 避免重复创建
    if (!$percent.length) {
        $percent = $('<div id="' + id_Prefix + '-progress" class="progress progress-striped active">' +
            '<div id="' + id_Prefix + '-progress-bar" class="progress-bar" role="progressbar" style="width: 0%">' +
            '</div>' +
            '</div>'
        ).appendTo($li).find('#' + id_Prefix + '-progress-bar');
    }
    var progressPercentage = percentage * 100 + '%';
    $percent.css('width', progressPercentage);
    $percent.html(titleName + ':' + progressPercentage);
}

/**
 * 隐藏进度条
 * @param file 文件对象
 * @param id_Prefix id前缀
 */
function fadeOutProgress(file, id_Prefix) {
    $('#' + file.id).find('#' + id_Prefix + '-progress').fadeOut();
}