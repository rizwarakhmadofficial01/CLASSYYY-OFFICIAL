document.addEventListener("DOMContentLoaded",()=>{
    document.querySelector(".hero")?.classList.add("show")
    })
    
    
    function toggleTheme(){
    document.body.classList.toggle("light")
    }
    
    
    function adminLogin(){
    const pass=prompt("Admin Password:")
    if(pass==="admin123"){
    alert("Admin Mode Aktif")
    }else{alert("Password Salah")}
    }