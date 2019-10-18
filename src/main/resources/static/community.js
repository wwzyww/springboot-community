/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
function comment2target(targetId,type,content) {
    $.ajax({
        type:"POST",
        url:"/insertComment",
        contentType: "application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        dataType:"json",
        success:function (response) {
            console.log(response);
            if (response.code == 100){
                //$("#comment_section").hide();
                window.location.reload();
            } else if (response.code == 300){
                var login = confirm(response.message);
                if (login){
                    window.open("https://github.com/login/oauth/authorize?client_id=ff03132c67d625b9f5af&redirect_url=http://localhost:8001/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            }else {
                alert(response.message);
            }
        }
    })
}
function comment(e) {
    console.log(e)
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    if(!content){
        alert("不能回复空...");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/insertComments",
        contentType: "application/json",
        data:JSON.stringify({
            "parentId":commentId,
            "content":content,
            "type":2
        }),
        dataType:"json",
        success:function (response) {
            console.log(response);
            if (response.code == 100){
                //$("#comment_section").hide();
                window.location.reload();
            } else{
                var login = confirm(response.message);
                if (login){
                    window.open("https://github.com/login/oauth/authorize?client_id=ff03132c67d625b9f5af&redirect_url=http://localhost:8001/callback&scope=user&state=1");
                    window.localStorage.setItem("closable",true);
                }
            }
        }
    })
}



/**
 * 展开二级评论
 */

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subComment = $("#comment-" + id);
        if (subComment.children().length != 1) {
            //展开
            comments.addClass("in");
            //标记状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/queryById/" + id, function (data) {
                console.log(data);
                $.each(data, function (index, comment) {

                    var mediaLefEl = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyEl = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.createTime).format('YYYY-MM-DD HH:mm')
                    })));

                    var mediaEl = $("<div/>", {
                        "class": "media",
                    }).append(mediaLefEl).append(mediaBodyEl);

                    var commentEl = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaEl);

                    subComment.prepend(commentEl);
                });
                //展开
                comments.addClass("in");
                //标记状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }

    }
}
    /**
     * 设置添加标签
     */
    function selectTag(e) {
        var value = e.getAttribute("data-tag");
        var tags = $("#tag").val();
        if(tags.split(",").indexOf(value) == -1) { //存在就不添加
        if(tags){
            $("#tag").val(tags + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}

/**
 *显示tag标签选择
 */
function showSelectTag() {
    $("#select-tag").show();
}

