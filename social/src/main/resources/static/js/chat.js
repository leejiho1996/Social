const modal = document.getElementById("chatModal");
const span = document.querySelector(".close");

function openChatModal() {
    const userId = document.getElementById("principalId").value;
    modal.style.display = "block";

    // 리스트 초기화
    $("#chatModalList").html("");

    $.ajax({
        url: `/api/user/${userId}/subscribe`,
        dataType: "json"
    }).done(res => {
        res.data.forEach((u) => {
            let item = getSubscribeModalChatItem(u);
            $("#chatModalList").append(item);
        });
    }).fail(error => {
        console.log("실패", error);
    });
}

function getSubscribeModalChatItem(u) {
    return `
    <div class="subscribe__item">
        <div class="left-section">
            <div class="subscribe__img">
                <img src="/upload/${u.profileImageUri}" onerror="this.src='/images/person.jpeg'" />
            </div>
            <div class="subscribe__text">
                <h2>${u.nickname}</h2>
            </div>
        </div>
        <div class="subscribe__btn">
            <button class="cta blue" onclick="startChat(${u.id}, '${u.nickname}')">대화하기</button>
        </div>
    </div>`;
}

span.onclick = function () {
    modal.style.display = "none";
};

window.onclick = function (event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

// 채팅모달에서 채팅시작 버튼 클릭 시 실행
function startChat(userId, nickname) {
    $.ajax({
        url: `/api/chat/room`,
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify({ targetUserId: userId }),
    }).done(res => {
        const roomId = res.roomId;
        loadChatRoom(roomId, nickname); // 👉 채팅 내용 바꾸기
    }).fail(err => {
        alert("채팅방 연결 실패");
        console.error(err);
    });
}

function loadChatRoom(roomId, nickname) {
    $.ajax({
        url: `/api/chat/room/${roomId}/messages`,
        method: "GET",
        dataType: "json"
    }).done(res => {
        $(".chat-header h2").text(nickname); // 제목 변경

        const chatBox = $(".chat-messages");
        chatBox.html(""); // 기존 내용 초기화

        res.messages.forEach(msg => {
            const messageClass = msg.senderId === parseInt($("#principalId").val()) ? "sent" : "received";
            const messageHtml = `<div class="message ${messageClass}">${msg.content}</div>`;
            chatBox.append(messageHtml);
        });

        // 스크롤 아래로
        chatBox.scrollTop(chatBox[0].scrollHeight);

        // 선택된 채팅방 스타일도 반영하려면 추가로 처리 가능
    }).fail(err => {
        console.log("메시지 로딩 실패", err);
    });
}

// 메시지 전송
function sendMessage() {
    const chatBox = $(".chat-messages");
    const input = $(".chat-input input");
    const message = input.val().trim();

    if (message === "") return; // 빈 메시지는 무시
    // 메시지 박스 추가
    const messageHtml = `<div class="message sent">${message}</div>`;
    chatBox.append(messageHtml);
    // 입력창 비우기
    input.val("");
    // 스크롤 맨 아래로
    chatBox.scrollTop(chatBox[0].scrollHeight);
}

// 엔터로 메시지 전송
$(".chat-input input").keypress(function (e) {
    if (e.key === "Enter") {
        sendMessage();
    }
});

