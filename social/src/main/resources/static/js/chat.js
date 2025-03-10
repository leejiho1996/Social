let stompClient = null;
let currentRoomId = null;

function connectWebSocket() {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log("웹 소켓 연결 성공");

        if (currentRoodId) {
            subscribeRoom(currentRoomId);
        }
    });
}

function subscribeRoom(roomId) {
    if (stompClient && stompClient.connected) {
        stompClient.subscribe(`/sub/chat/room/${roomId}`, (message) => {
            const msg = JSON.parse(message.body);
            const messageClass = msg.senderId === parseInt($("#principalId").val()) ? "sent" : "received";
            const html = `<div class="message ${messageClass}">${msg.content}</div>`;
            $(".chat-messages").append(html);
        });
    }
    currentRoomId = roomId;
}

function loadChatRoom(roomId, nickname) {
    // 1. 기존 메시지 지우고 UI 초기화
    $(".chat-header h2").text(nickname);
    $(".chat-messages").html("");

    // 2. 메시지 내역 요청
    $.ajax({
        url: `/api/chat/room/${roomId}/messages`,
        method: "GET",
        dataType: "json"
    }).done(res => {
        res.messages.forEach(msg => {
            const messageClass = msg.senderId === parseInt($("#principalId").val()) ? "sent" : "received";
            const html = `<div class="message ${messageClass}">${msg.content}</div>`;
            $(".chat-messages").append(html);
        });

        // 3. 스크롤 맨 아래로
        const chatBox = $(".chat-messages");
        chatBox.scrollTop(chatBox[0].scrollHeight);

        // 4. 새로운 방 구독
        if (currentRoomId !== roomId) {
            currentRoomId = roomId;
            subscribeRoom(roomId);
        }
    });
}

// 메시지 전송
function sendMessage() {
    const input = $(".chat-input input");
    const content = input.val().trim();
    if (!content || !currentRoomId) return;

    const message = {
        roomId: currentRoomId,
        senderId: parseInt($("#principalId").val()),
        content: content
    };

    stompClient.send("/pub/chat.sendMessage", {}, JSON.stringify(message));
    input.val("");
}


// 엔터로 메시지 전송
$(".chat-input input").keypress(function (e) {
    if (e.key === "Enter") {
        sendMessage();
    }
});

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

// 모달 부분
// ================================================================
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

