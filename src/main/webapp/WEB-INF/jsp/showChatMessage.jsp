<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="IncludeTopDist.jsp" %>
<body>
<div id="layoutDefault">
    <section id="layoutDefault_content">
        <%@ include file="IncludeQuickHeader.jsp" %>
        <main class="vh-100">
            <header class="page-header page-header-light bg-light h-100 overflow-scroll">
                <div class="container h-100">
                    <a href="<c:url value="/chat/room/${userSession.userInfo.userId}"/>">
                        <i class="fas fa-angle-left fa-2x"></i>
                        <h1 class="ml-2 d-inline font-weight-bolder">
                            <c:out value="${chatRoomName}"></c:out>
                        </h1>
                    </a>
                    <main class="main-screen main-chat mt-5 h-75">
                        <c:forEach var="message" items="${chatMessageList}">
                            <div>
                                <c:if test="${userSession.getUserInfo().getUserId() eq message.receiverId}">
                                    <div class="message-row leftMessage">
                                        <div class="message-row__content mt-3">
                                            <span class="message__author text-primary font-weight-bolder">
                                                <c:out value="${message.senderNickName}"></c:out>
                                            </span>
                                            <div class="message__info">
                                                <span class="message__bubble">
                                                    <c:out value="${message.content}"></c:out>
                                                </span>
                                            </div>
                                            <span class="message__time">
                                                <fmt:formatDate value="${message.createdDateTime}"
                                                                pattern="yyyy/MM/dd hh:mm"/>
                                            </span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${userSession.getUserInfo().getUserId() eq message.senderId}">
                                    <div class="mr-3 rightMessage">
                                        <div class="message-row__content message-row--own mt-3">
                                            <div class="message-row__content">
                                                <div class="message__info">
                                                    <span class="message__bubble">
                                                    <c:out value="${message.content}"></c:out>
                                                    </span>
                                                </div>
                                                <span class="message__time float-end">
                                                        <fmt:formatDate value="${message.createdDateTime}"
                                                                        pattern="yyyy/MM/dd hh:mm"/>
                                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </main>
                </div>
                <form class="reply">
                    <div class="reply__column">
                        <input type="text" placeholder="Write a message..." id="message" onkeyup="send()" autofocus/>
                        <button id="send_message_btn" class="bg-gray-400" onclick="send()">
                            <i class="fas fa-arrow-up text-light"></i>
                        </button>
                    </div>
                </form>
            </header>
        </main>
    </section>
</div>
<!-- end chat area-->

<script
        src="https://kit.fontawesome.com/6478f529f2.js"
        crossorigin="anonymous"
></script>
<script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script>
    /*<![CDATA[*/
    let Message = document.getElementById("message");
    $("#send_message_btn").click(function () {
        sendMessage();
    });

    function send() {
        if (window.event.keyCode === 13) {
            sendMessage(); //실행 명령
        }
    }

    function sendMessage() {
        const btn = $(this);
        if (btn.hasClass("btn-loading")) return;

        let content = $('#message').val();
        let postIdx = '<c:out value="${post.postIdx}"></c:out>';
        let senderId = '<c:out value="${userId}"></c:out>';
        let receiverId = '<c:out value="${chatRoomId}"></c:out>';
        let chatRelationIdx = '<c:out value="${chatRelationIdx}"></c:out> '

        // alert(receiverId);
        //문자열 좌우 공백 제거
        if (content.trim() === '') {
            content.focus();
            return;
        }

        btn.addClass("btn-loading").attr("disabled", true);

        let data = {
            postIdx: postIdx,
            senderId: senderId,
            receiverId: receiverId,
            readYn: 'N',
            content: content,
            chatRelationIdx: chatRelationIdx
        };

        var reqUrl = "../../../send/room/message.do";
        $.ajax({
            url: reqUrl,
            type: 'POST',
            async: false,
            data: JSON.stringify(data),
            contentType: 'application/json; charset = utf-8',
            dataType: 'json', // 리턴해주는 타입을 지정해줘야함
            success: function (res) {
                console.log("호출성공");
                console.log(JSON.parse(res)); //찾아보기
                location.reload();
            },// 요청 완료 시
            error: function (err) {
                console.log("채팅 전송 실패입니다.");
                alert("채팅 전송에 실패하였습니다.");
                btn.removeClass("btn-loading").attr("disabled", false);
            }// 요청 실패.
        });
    }

    /*]]>*/
</script>

</body>
</html>