function getcookie(objname) {
    var arrstr=document.cookie.split(";");
    for (var i = 0; i < arrstr.length; i++) {
        var temp=arrstr[i].split("=");
        if (temp[0]=="roomNumber") return unescape(temp[1]);
    }
}

const res = XMLHttpRequest();
res.cookie("roomNumber",)
console.log()
