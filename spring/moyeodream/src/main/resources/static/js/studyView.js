$(document).ready(function() {
    $.ajax({
        url: "/studyComment/list/" + studyNumber,
        type: "GET",
        dataType: "json",
        success: function(result) {
            $commentContent = $("ul.comment-list");
            for(let i in result) {
                let comment = "";
                comment += "<li class=\"comment\">";
                comment += "    <section class=\"comment-info-wrap\">";
                comment += "        <div class=\"comment-info\">";
                comment += "            <img src=\"https://hola-post-image.s3.ap-northeast-2.amazonaws.com/default.PNG\">";
                comment += "            <div class=\"comment-title-wrap\">";
                comment += "                <div class=\"comment-title\">";
                comment += "                    <div class=\"comment-user\">" + result[i].memberNickname + "</div>";
                comment += "                    <div class=\"comment-registered-date\">" + result[i].studyCommentRegisterDate + "</div>";
                comment += "                </div>";
                comment += "            </div>";
                comment += "        </div>";
                comment += "        <section class=\"comment-button-wrap\">";
                comment += "            <button type=\"button\" class=\"comment-button comment-modify\">수정</button>";
                comment += "            <button type=\"button\" class=\"comment-button comment-remove\">삭제</button>";
                comment += "        </section>";
                comment += "    </section>";
                comment += "    <section class=\"comment-content-wrap\">";
                comment += "        <p class=\"comment-content\">" + result[i].studyCommentContent + "</p>";
                comment += "        <div class=\"comment-revise\">";
                comment += "            <input type=\"text\" value=\"" + result[i].studyCommentContent + "\" placeholder=\"" + result[i].studyCommentContent +"\" name=\"commentRevise\">";
                comment += "            <div class=\"comment-revise-buttons\">";
                comment += "                <button class=\"comment-revise-button cancel\">취소</button>";
                comment += "                <button class=\"comment-revise-button register\">완료</button>";
                comment += "            </div>";
                comment += "        </div>";
                comment += "    </section>";
                comment += "</li>";
                $commentContent.append(comment);
            }

            $modifyButton = $("button.comment-modify");
            $cancelButton = $("button.cancel");

            // $removeButton = $("button.comment-remove");

            $modifyButton.on("click", function() {
                $(this).parent().parent().next().children("p").css("display", "none");
                $(this).parent().parent().next().children("div").css("display", "block");
            });

            $cancelButton.on("click", function() {
                $(this).parent().parent().prev().css("display", "block");
                $(this).parent().parent().css("display", "none");
            });
        }
    });

    $("div.study-registered-date").text(dateFormat(studyRegisterDate));
    $("span.study-start").text(dateFormat(studyStartDate));

    $("div.user-introduction").append(studyChief);
    $("div.study-introduction").append(studyContent);

    if(studyCategory == "culture") {
        $("span.study-cate").text("문화/예술");
    }
    else if(studyCategory == "sports") {
        $("span.study-cate").text("스포츠/레저");
    }
    else if(studyCategory == "computer") {
        $("span.study-cate").text("컴퓨터/프로그래밍");
    }
    else if(studyCategory == "language") {
        $("span.study-cate").text("외국어/토익/토플");
    }
    else if(studyCategory == "humanities") {
        $("span.study-cate").text("인문/과학");
    }
    else if(studyCategory == "economy") {
        $("span.study-cate").text("경제/금융");
    }
    else if(studyCategory == "politics") {
        $("span.study-cate").text("정치/사회");
    }
    else if(studyCategory == "certificate") {
        $("span.study-cate").text("자격증");
    }
    else if(studyCategory == "publicOfficial") {
        $("span.study-cate").text("공무원");
    }
    else if(studyCategory == "employment") {
        $("span.study-cate").text("취업/면접");
    }

    if(studyProcess == "online") {
        $("span.study-process").text("온라인");
    }
    else if(studyProcess == "offline") {
        $("span.study-process").text("오프라인");
    }

    if(studyPersonnel == "none") {
        $("span.study-people").text("인원 미정");
    }
    else if(studyPersonnel == "one") {
        $("span.study-people").text("1명");
    }
    else if(studyPersonnel == "two") {
        $("span.study-people").text("2명");
    }
    else if(studyPersonnel == "three") {
        $("span.study-people").text("3명");
    }
    else if(studyPersonnel == "four") {
        $("span.study-people").text("4명");
    }
    else if(studyPersonnel == "five") {
        $("span.study-people").text("5명");
    }
    else if(studyPersonnel == "six") {
        $("span.study-people").text("6명");
    }
    else if(studyPersonnel == "seven") {
        $("span.study-people").text("7명");
    }
    else if(studyPersonnel == "eight") {
        $("span.study-people").text("8명");
    }
    else if(studyPersonnel == "nine") {
        $("span.study-people").text("9명");
    }
    else if(studyPersonnel == "tenUp") {
        $("span.study-people").text("10명 이상");
    }

    if(studyPeriod == "none") {
        $("span.study-period").text("기간 미정");
    }
    else if(studyPeriod == "oneMonth") {
        $("span.study-period").text("1개월");
    }
    else if(studyPeriod == "twoMonths") {
        $("span.study-period").text("2개월");
    }
    else if(studyPeriod == "threeMonths") {
        $("span.study-period").text("3개월");
    }
    else if(studyPeriod == "fourMonths") {
        $("span.study-period").text("4개월");
    }
    else if(studyPeriod == "fiveMonths") {
        $("span.study-period").text("5개월");
    }
    else if(studyPeriod == "sixMonths") {
        $("span.study-period").text("6개월");
    }
    else if(studyPeriod == "longTerm") {
        $("span.study-period").text("장기");
    }
});

$("a.kakao-openchatting").click(function() {
    location.href = studyCommunication;
});

function dateFormat(format) {
    let date = new Date(format);
    return date.getFullYear() + "-" + ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1).toString() : "0" + (date.getMonth() + 1)) + "-" + (date.getDate() > 9 ? date.getDate().toString() : "0" + date.getDate().toString());
}

function clickModify() {
    location.href = "/study/modify?studyNumber=" + studyNumber;
}

function clickRemove() {
    location.href = "/study/remove?studyNumber=" + studyNumber;
}