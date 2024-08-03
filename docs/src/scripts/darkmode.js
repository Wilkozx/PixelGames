const darkmodeToggle = document.querySelector('#darkmode-toggle');

if (localStorage.getItem('darkmode') === 'true') {
    toggleDarkmode();
}

darkmodeToggle.addEventListener('click', () => {
    toggleDarkmode();
});

function toggleDarkmode() {
    document.body.classList.toggle('darkmode');
    darkmodeToggle.src = document.body.classList.contains('darkmode') ? 'src/icons/sun.svg' : 'src/icons/moon.svg';
    darkmodeToggle.alt = document.body.classList.contains('darkmode') ? 'lightmode' : 'darkmode';
    localStorage.setItem('darkmode', document.body.classList.contains('darkmode'));
}