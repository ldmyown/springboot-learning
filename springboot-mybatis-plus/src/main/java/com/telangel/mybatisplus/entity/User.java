package com.telangel.mybatisplus.entity;

        import com.baomidou.mybatisplus.annotation.IdType;
        import com.baomidou.mybatisplus.annotation.TableId;
        import com.baomidou.mybatisplus.annotation.TableName;
        import lombok.Data;

/**
 * @author： lid
 * @date： 2019/9/26 17:03
 */
@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String sex;
    private Integer age;
    private String email;
}
