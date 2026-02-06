document.addEventListener("DOMContentLoaded", () => {
    const hero = document.querySelector(".hero");
    hero.classList.add("show");
  });
  
  const password = "admin123";

  function adminLogin() {
    const input = prompt("Admin Password:");
    if (input === password) {
      document.body.classList.add("admin");
      alert("Admin Mode Aktif");
    } else {
      alert("Password Salah");
    }
  }
  