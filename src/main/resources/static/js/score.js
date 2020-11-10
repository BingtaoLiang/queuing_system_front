(function () {
    document.getElementById("sub").onclick = function () {
        var r = document.getElementById("eval").getElementsByTagName("input");
        var sc = document.getElementById("sc").value;
        var name = document.getElementById("name").value;
        var age = document.getElementById("age").value;
        var sex = document.getElementById("sex").value;
        var phone = document.getElementById("phone").value;
        var sum = 0;
        var len = r.length;
        for (var i = 0; i < len; i++) {
            if (r[i].checked) {
                sum += parseFloat(r[i].value);
            }
        }
        var su = Number(sum) + Number(sc);

        var info = {"score": su, "username": name, "userAge": age, "userSex": sex, "userPhone": phone};

        $.ajaxSetup({caches: false});//禁止缓存
        $.ajax({
            type: "post",
            url: "/test",
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',// 核心
            data: JSON.stringify(info),
            // data: {'score': su,'username':name,'userAge':age,'userSex':sex,'userPhone':phone},
            success: function (data) {
                console.log(data);
                window.sessionStorage.setItem("username", data.get(username));
                window.sessionStorage.setItem("userAge", data.get(userAge));
                window.sessionStorage.setItem("userSex", data.get(userSex));
                window.sessionStorage.setItem("userPhone", data.get(userPhone));
                window.sessionStorage.setItem("userScore", data.get(userScore));

                window.location.href = "/en";
            },
            error: function () {
                alert("遇到了一个问题");

                // console.log("有问题");
            }
        });

        // alert("您的最终测试结果为：" + su + "分");
        alert("确认要提交吗")
        if (confirm("请确认所有题都回答完毕？")) {
            alert("您的最终测试结果为：" + su + "分");
        } else {
            alert("返回答题");
        }
    }
})();