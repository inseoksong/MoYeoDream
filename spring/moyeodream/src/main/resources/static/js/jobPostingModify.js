const imgThumbnail = document.querySelector("label[for='enterprise-img'] img");
const imgFile = document.querySelector("input[id='enterprise-img']");

imgFile.addEventListener("change", function(event) {
    let reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    reader.onload = function(event) {
        let url = event.target.result;
        if(url.includes("image")) {
            imgThumbnail.src = url;
        }
        else {
            imgThumbnail.src = "https://hola-post-image.s3.ap-northeast-2.amazonaws.com/default.PNG";
        }
    }
});




const $inputTitle = $("input#title-input");
const $companyName = $("input#enterprise-input");
const $career = $("input#employment-time-input");
const $worktype = $("input#employment-regular-input");
const $Area = $("input#region-input");
const $CompanyUrl = $("input#site-input");
const $note = $(".summernote");
const $input = $("input.jobposting-content");
const $registerButton = $("button.register");


$registerButton.on("click", function () {
    let note = $note.summernote('code');

    if($companyName.val() == "") {
        alert("기업 이름을 입력해주세요.");
    }
    else if($career.val() == "") {
        alert("채용 경력을 입력해주세요.");
    }
    else if($worktype.val() == "") {
        alert("채용 형태를 입력해주세요.");
    }
    else if($Area.val() == "") {
        alert("근무 지역을 입력해주세요.");
    }
    else if($CompanyUrl.val() == "") {
        alert("사이트 url를 입력해주세요.");
    }
    else if($inputTitle.val() == "") {
        alert("공고 제목을 선택해주세요.");
    }
    else if(note == "<p><br></p>" || note == "") {
        alert("공고 상세 내용을 입력해주세요.");
    }
    else {
        $input.val(note);
        $("#createForm").submit();
    }
});