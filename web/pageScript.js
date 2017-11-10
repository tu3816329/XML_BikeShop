/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function changePicture(path,picture, price) {
    var priceTag = document.getElementById("price");
    var img = document.getElementById("ActiveImage");
    priceTag.innerHTML=price+' VNĐ';
    img.src=path+picture;
}
function showLogin() {
    var div = document.getElementById("loginDiv");
    var bodyDiv = document.getElementById("bodyDiv");
    bodyDiv.style.opacity = 0.1;
    div.style.display = "block";
    div.style.zIndex = "1";
}
function backToPage() {
    var div = document.getElementById("loginDiv");
    div.style.display = "none";
    var bodyDiv = document.getElementById("bodyDiv");
    bodyDiv.style.opacity = 1;
    div.style.zIndex = "-1";
}
