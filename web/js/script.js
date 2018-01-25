function validate_admin() {
    var aid=sessionStorage.getItem("role");
    if(aid!=4)
        window.location="http://localhost:8080/Feedback_System_war_exploded/index.html";
}