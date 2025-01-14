package com.project.moyeodream.controller;

import com.project.moyeodream.domain.vo.*;
import com.project.moyeodream.mapper.PostMapper;
import com.project.moyeodream.service.PostService;
import com.project.moyeodream.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post/*")
public class PostController {
    private final PostServiceImpl postService;
    private final PostMapper postMapper;

    // 모든 자유 게시판 목록
    @GetMapping("list")
    public String postList(Model model, Criteria criteria, HttpServletRequest req){
        log.info("--------------------------------------------------");
        log.info("getList Controller...............");
        log.info("Criteria............." + criteria);
        log.info("--------------------------------------------------");

        int memberNum = 0;
        HttpSession session = req.getSession();

        if(session.getAttribute("memberNumber") != null){
            memberNum = (Integer)session.getAttribute("memberNumber");
        } else{
            memberNum = -1;
        }
        model.addAttribute("session", memberNum);

        criteria.setAmount(5);
        PageDTO pageDTO;

        if(criteria.getType() == null){
            criteria.setType("default");
        }

        if(criteria.getType().equals("R")){
            pageDTO = new PageDTO(criteria, postMapper.getReplyTotal(criteria));
            model.addAttribute("postList",postMapper.getReplyKeyword(criteria));
        }else{
            pageDTO = new PageDTO(criteria, postService.getTotal(criteria));
            model.addAttribute("postList",postService.getList(criteria));
        }

        model.addAttribute("pageDTO", pageDTO );
        model.addAttribute("criteria", criteria);

        log.info("-------------------------------------------------");
        log.info(pageDTO.getCriteria().getListLink());
        log.info(pageDTO.toString());

        return "/board/board";
    }

    // 게시글 상세 조회
    @GetMapping("read")
    public String postRead(Integer postNumber, Criteria criteria, Model model, HttpServletRequest req){
        log.info("--------------------------------------------------");
        log.info("read Controller...............");
        log.info("Criteria............." + criteria);
        log.info("--------------------------------------------------");

        int memberNum = 0;
        HttpSession session = req.getSession();

        if(session.getAttribute("memberNumber") != null){
            memberNum = (Integer)session.getAttribute("memberNumber");
        } else{
            memberNum = -1;
        }

        // 상세보기 들어오면 조회수 1 UP
        model.addAttribute("post",postService.postRead(postNumber));
        model.addAttribute("criteria",criteria);
        model.addAttribute("session", memberNum);

        return "/board/boardDetail";
    }

    // 카테고리별 자유 게시판 조회
    @GetMapping("readCategory")
    public void readCategory(String postCategory){}

    // 내 자유 게시판 글 확인
    @GetMapping("myPost")
    public void myPost(Integer memberNumber){}

    // 자유게시판 작성
    @PostMapping("register")
    public void postRegister(PostVO postVO){}

    // 자유게시판 수정화면 불러오기
    @GetMapping("modify")
    public String goModify(Criteria criteria, Integer postNumber, Model model, HttpServletRequest req){
        log.info("-----------------------------------------------");
        log.info("go Modify Controller........................");
        log.info("criteria ........" + criteria);
        log.info("-----------------------------------------------");

        int memberNum = 0;
        HttpSession session = req.getSession();

        if(session.getAttribute("memberNumber") != null){
            memberNum = (Integer)session.getAttribute("memberNumber");
        } else{
            memberNum = -1;
        }
        model.addAttribute("session", memberNum);

        model.addAttribute("post",postService.postRead(postNumber));
        model.addAttribute("criteria",criteria);

        return "/board/boardModify";
    }

    // 자유게시판 수정 완료
    @PostMapping("modify")
    public RedirectView postModify(PostVO postVO, Criteria criteria, RedirectAttributes rttr, HttpServletRequest req){
        log.info("---------------------------------------------------");
        log.info("modifyOk controller..................");
        log.info("criteria..........................."+ criteria);
        log.info("---------------------------------------------------");

        log.info(" 받아온 컨텐츠 내용 : " + postVO.getPostContent());
        postService.postUpdate(postVO);

        int memberNum = 0;
        HttpSession session = req.getSession();

        if(session.getAttribute("memberNumber") != null){
            memberNum = (Integer)session.getAttribute("memberNumber");
        } else{
            memberNum = -1;
        }
        rttr.addAttribute("session", memberNum);


        rttr.addAttribute("postNumber", postVO.getPostNumber());
        rttr.addFlashAttribute("criteria", criteria);

        return new RedirectView("/post/read");
    }

    // 자유게시판 삭제
    @GetMapping("remove")
    public RedirectView postRemove(Integer postNumber, Criteria criteria){
        log.info("---------------------------------------------------");
        log.info("delete controller..................");
        log.info("criteria..........................."+ criteria);
        log.info("---------------------------------------------------");

        postService.postDelete(postNumber);

        return new RedirectView("/post/list");
    }

    // --- 프론트 ---

    // 게시판 메인
    @GetMapping("board")
    public String board(){ return "board/board";}

    // 게시판 세부사항
    @GetMapping("boardDetail")
    public String boardDetail(){ return "board/boardDetail";}

    // 게시판 글쓰기
    @GetMapping("postRegister")
    public String postRegister(){
        return "/board/boardWrite";}

    /* 게시판 등록 완료*/
    @PostMapping("postRegister")
    public String register(PostVO postVO, Model model, Criteria criteria, HttpServletRequest req){
        log.info("--------------------------------------------------");
        log.info("post Register ........" + postVO);
        log.info("--------------------------------------------------");

        // 세션에 저장된 memberNumber 가져오기
        HttpSession session = req.getSession();
        log.info("세션에 저장된 번호 : " + (Integer)session.getAttribute("memberNumber"));
        postVO.setPostMemberNumber((Integer)session.getAttribute("memberNumber"));
        log.info("작성자 memberNum : "+postVO.getPostMemberNumber());

        postService.postRegister(postVO);
        log.info("새로 등록한 게시글 번호" + postVO.getPostNumber());
        model.addAttribute("postNumber", postVO.getPostNumber());
        model.addAttribute(criteria);

        return postList(model, criteria, req);
    }
}
