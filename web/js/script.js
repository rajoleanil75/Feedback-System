function validate_admin() {
    var aid=sessionStorage.getItem("role");
    if(aid!=4)
        window.location="http://localhost:8080/Feedback_System_war_exploded/index.html";
}
function validate_stud() {
    var sid=sessionStorage.getItem("role");
    if(sid!=1)
        window.location="http://localhost:8080/Feedback_System_war_exploded/index.html";
}
function validate_hod() {
    var sid=sessionStorage.getItem("role");
    if(sid!=3)
        window.location="http://localhost:8080/Feedback_System_war_exploded/index.html";
}