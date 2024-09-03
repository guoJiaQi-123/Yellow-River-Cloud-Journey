package com.tyut.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyut.travel.mapper.MemberMapper;
import com.tyut.travel.pojo.Member;
import com.tyut.travel.pojo.Result;
import com.tyut.travel.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MemberService {

    private final MemberMapper memberMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MailUtils mailUtils;

    @Value("${project.path}")
    private String projectPath;

    public MemberService(MemberMapper memberMapper, BCryptPasswordEncoder bCryptPasswordEncoder, MailUtils mailUtils) {
        this.memberMapper = memberMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailUtils = mailUtils;
    }

    public Result register(Member member){
        //1.保存用户
            //1.1 验证用户名是否重复
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Member> usernameWrapper = queryWrapper.eq("username", member.getUsername());
        List<Member> members = memberMapper.selectList(usernameWrapper);
        if(!members.isEmpty()){
            return new Result(false,"用户名已存在");
        }
            //1.2 验证邮箱是否重复
        QueryWrapper<Member> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<Member> emailWrapper = queryWrapper1.eq("email", member.getEmail());
        List<Member> members1 = memberMapper.selectList(emailWrapper);
        if(!members1.isEmpty()){
            return new Result(false,"邮箱已存在");
        }
            //1.3 验证手机号是否重复
        QueryWrapper<Member> queryWrapper2 = new QueryWrapper<>();
        QueryWrapper<Member> phoneNumWrapper = queryWrapper2.eq("phoneNum", member.getPhoneNum());
        List<Member> members2 = memberMapper.selectList(phoneNumWrapper);
        if(!members2.isEmpty()){
            return new Result(false,"手机号已存在");
        }
            //1.4密码加密
        String password = member.getPassword();
        password = bCryptPasswordEncoder.encode(password);
        member.setPassword(password);
            //1.5设置是否激活active属性为FALSE
        member.setActive(false);

        //2.发送激活邮件
        //生成激活码
        String activeCode = UUID.randomUUID().toString();
        //收件人
        String to = member.getEmail();
        // 给用户的邮箱发送一封邮件，该邮件包含一个链接，链接中包含激活码
        String activeUrl = projectPath+"/frontdesk/member/active?activeCode="+activeCode;
        String text = "尊敬的客户，<br/>\n" +
                "<br/>\n" +
                "感谢您选择阡陌云旅作为您的旅行伙伴！我们非常荣幸能够为您提供优质的服务，让您能够享受到真正独特的农村旅游体验。<br/>\n" +
                "<br/>\n" +
                "为了确保您能够充分利用我们平台的全部功能和福利，请激活您的账户。只需点击以下链接进行激活：<a href='"+activeUrl+"/'>点击激活</a><br/>\n" +
                "<br/>\n" +
                "一旦您激活了账户，您将可以轻松搜索并预订您心仪的农村旅游目的地，参加丰富多样的农村活动，还能享受到各种特别优惠和折扣。<br/>\n" +
                "<br/>\n" +
                "除此之外，我们还将定期推送最新的农村旅游资讯、独家线路和精彩活动给您，让您第一时间了解最新农村旅游动态。<br/>\n" +
                "<br/>\n" +
                "借此机会，再次感谢您选择阡陌云旅。我们将竭诚为您提供最优质的服务，让您的农村旅行成为难忘的回忆。<br/>\n" +
                "<br/>\n" +
                "如有任何疑问或需要帮助，随时联系我们的客服团队。我们将尽快为您解答和处理。<br/>\n" +
                "<br/>\n" +
                "祝您旅途愉快！<br/>\n" +
                "<br/>\n" +
                "阡陌云旅注册成功， 完成账号认证。";
        mailUtils.sendMail(to,text,"阡陌云旅激活邮件");
        // 保存激活码，激活时比对
        member.setActiveCode(activeCode+"/");
        //添加用户
        memberMapper.insert(member);
        return new Result(true,"注册成功！");
    }

    public String active(String activeCode){
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activeCode", activeCode);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member==null){
            return "激活失败！激活码错误!";
        }else {
            member.setActive(true);
            memberMapper.updateById(member);
            return "激活成功，请<a href='"+projectPath+"/frontdesk/login'>登录</a>";
        }

    }

    //用户登录
    @SuppressWarnings("all")
    public Result login(String name,String password){
        Member member = null;
        //通过用户名查找
        if(member==null){
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        //通过电话号查找
        if(member==null){
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phoneNum",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        //通过邮箱查找
        if(member==null){
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        if(member==null){
            return new Result(false,"用户名或密码错误");
        }

        //校验密码
        boolean matches = bCryptPasswordEncoder.matches(password, member.getPassword());
        if(!matches){
            return new Result(false,"用户名或密码错误");
        }

        //验证是否激活
        if(!member.isActive()){
            return new Result(false,"账号未激活，请先登入邮箱激活");
        }

        return new Result(true,"登录成功！",member);
    }

}
