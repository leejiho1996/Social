/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {

	const subscribeCountSpan = document.querySelector(".subscribe_cnt");

	if ($(obj).text() === "구독취소") {
		$.ajax({
			type: "delete",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");

		}).fail(error => {
			console.log("구독취소 실패", error)
		})

	} else {
		$.ajax({
			type: "post",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");

		}).fail(error => {
			console.log("구독 실패", error)
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");

	$.ajax({
		url : `/api/user/${pageUserId}/subscribe`,
		dataType : "json"
	}).done(res => {
		console.log("성공", res.data)

		res.data.forEach((u) => {
			let item = getSubscribeModalItem(u);
			$("#subscribeModalList").append(item);
		});

	}).fail(error => {
		console.log("실패", error)
	})
}

function getSubscribeModalItem(u) {
	let item = `
    <div class="subscribe__item" id="subscribeModalItem-1">
    <div class="subscribe__img">
        <img src="/upload/${u.profileImageUri}" onerror="this.src='/images/person.jpeg'" />
    </div>
		<div class="subscribe__text">
			<h2>${u.nickname}</h2>
		</div>
    <div class="subscribe__btn">`;

	if (!u.equalUserState) { // 로그인 유저와 모달의 유저가 동일 인물이 아닐때
		if (u.subscribeState) {
			item += `<button class="cta blue" onClick="toggleSubscribe(${u.id}, this)">구독취소</button>`
		} else {
			item += `<button class="cta" onClick="toggleSubscribe(${u.id}, this)">구독하기</button>`
		}
	}


	item += `</div>
</div>`;
	return item;
}

// (4) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {


	if (pageUserId != principalId) {
		alert("수정할 권한이 없습니다.")
		return
	}

	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 이미지 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		console.log(profileImageForm);

		// Ajax로 form데이터를 전송하기 위해 FormData 객체에 담기.
		let formData = new FormData(profileImageForm);

		$.ajax({
			type: "put",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false, // multipart/form-data로 보내기위해 false
			processData: false, // ajax로 보내기위해선 이것도 false
			enctype: "multipart/form-data",
			dataType: "json"
		}).done(res => {
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
			alert("프로필사진이 성공적으로 변경되었습니다.");
		}).fail(error => {
			console.log("오류", error);
		});
	});
}


// (5) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (8) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}







