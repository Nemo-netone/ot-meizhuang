function personalPage() {
    let user = JSON.parse(localStorage.getItem("user"));
    if (!user) {
        alert('请登录');
		window.location = '/front/login.html';
    }
	if (user && user.level && user.level === 1) {
		window.location = '/front/accountAdminInfo.html';
	}
	if (user && user.level && user.level === 2) {
		window.location = '/front/accountBusinessInfo.html';
	}
	if (user && user.level && user.level === 3) {
		window.location = '/front/accountUserInfo.html';
	}

}
function enterEndPage(level) {
	localStorage.setItem('level', level);
	window.location = '/end/page/login.html';
}
function enterRegister(level) {
	localStorage.setItem('level', level);
	window.location = '/end/page/register.html';
}
