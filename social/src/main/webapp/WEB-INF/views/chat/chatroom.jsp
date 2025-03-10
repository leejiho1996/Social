<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="chat-page">
    <!-- ✅ 모달: 구독자 목록 기반 채팅 시작 -->
    <div id="chatModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>새 채팅 시작</h3>
            <div id="chatModalList" class="chat-list-scroll"></div>
        </div>
    </div>

    <!-- ✅ 전체 채팅 레이아웃 -->
    <div class="chat-container">
        <!-- 채팅 목록 -->
        <div class="chat-list">
            <h2>채팅</h2>
            <div class="chat-item">
                <img src="https://via.placeholder.com/50" alt="avatar">
                <div class="chat-info">
                    <p><strong>친구 1</strong></p>
                    <p>안녕!</p>
                </div>
            </div>
            <div class="chat-item">
                <img src="https://via.placeholder.com/50" alt="avatar">
                <div class="chat-info">
                    <p><strong>친구 2</strong></p>
                    <p>오늘 뭐해?</p>
                </div>
            </div>
            <button class="add-chat" onclick="openChatModal()">+ 채팅 추가</button>
        </div>

        <!-- 채팅창 -->
        <div class="chat-window">
            <div class="chat-header">
                <h2>채팅방</h2>
            </div>
            <div class="chat-messages">
                <div class="message received">안녕!</div>
                <div class="message sent">안녕! 잘 지내?</div>
            </div>
            <div class="chat-input">
                <input type="text" placeholder="메시지를 입력하세요...">
                <button onclick="sendMessage()">
                    <i class="fas fa-paper-plane"></i>
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 현재 로그인한 유저 ID -->
<input type="hidden" id="principalId" value="${principal.user.id}">
<script src="/js/chat.js"></script>
