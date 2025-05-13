// Global variables
let verses = {};
let currentTheme = 'light';

// DOM elements
const timeElement = document.getElementById('time');
const ampmElement = document.getElementById('ampm');
const referenceElement = document.getElementById('reference');
const verseElement = document.getElementById('verse');
const themeToggle = document.getElementById('themeToggle');

// Helper function to convert time string to minutes
function timeToMinutes(timeStr) {
    const [hours, minutes] = timeStr.split(':').map(Number);
    return hours * 60 + minutes;
}

// Helper function to find closest verse
function findClosestVerse(targetTime) {
    const targetMinutes = timeToMinutes(targetTime);
    let closestKey = null;
    let minDiff = Infinity;

    // Convert all verse keys to minutes and find the closest one that's not after target time
    Object.keys(verses).forEach(key => {
        const verseMinutes = timeToMinutes(key);
        const diff = targetMinutes - verseMinutes;
        
        // Only consider verses that are not after the target time
        if (diff >= 0 && diff < minDiff) {
            minDiff = diff;
            closestKey = key;
        }
    });

    return closestKey;
}

// Load verse from Spring Boot endpoint
async function loadVerses() {
    try {
        const now = new Date();
        const hours = now.getHours();
        const minutes = now.getMinutes();
        const timeString = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
        
        const response = await fetch(`http://localhost:8080/book/bible/${timeString}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const verseData = await response.json();
        
        // Store the verse in our verses object
        const key = `${hours % 12 || 12}:${minutes.toString().padStart(2, '0')}`;
        verses[key] = [{
            book: verseData.book,
            text: verseData.text
        }];

        updateDisplay();
    } catch (error) {
        console.error('Error loading verse:', error);
        verseElement.innerHTML = '<p>Error loading verse. Please try again later.</p>';
    }
}

// Update time and verse display
function updateDisplay() {
    const now = new Date();
    const hours = now.getHours();
    const minutes = now.getMinutes();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    const displayHours = hours % 12 || 12;
    
    // Update time display
    timeElement.textContent = `${displayHours}:${minutes.toString().padStart(2, '0')}`;
    ampmElement.textContent = ampm;

    // Get verse for current time
    const verseKey = `${displayHours}:${minutes.toString().padStart(2, '0')}`;
    const matchingVerses = verses[verseKey];

    if (matchingVerses && matchingVerses.length > 0) {
        // Show up to 3 matching verses
        const versesToShow = matchingVerses.slice(0, 3);
        referenceElement.textContent = versesToShow
            .map(v => `${v.book} ${verseKey}`)
            .join(' • ');
        verseElement.innerHTML = versesToShow
            .map(v => `<p>${v.text}</p>`)
            .join('');
    } else {
        // Find closest verse
        const closestKey = findClosestVerse(verseKey);
        if (closestKey) {
            const closestVerses = verses[closestKey];
            const versesToShow = closestVerses.slice(0, 3);
            referenceElement.textContent = versesToShow
                .map(v => `${v.book} ${closestKey}`)
                .join(' • ');
            verseElement.innerHTML = versesToShow
                .map(v => `<p>${v.text}</p>`)
                .join('');
        } else {
            referenceElement.textContent = '';
            verseElement.innerHTML = `<p>No verse found for ${verseKey}</p>`;
        }
    }
}

// Theme toggle functionality
function toggleTheme() {
    currentTheme = currentTheme === 'light' ? 'dark' : 'light';
    document.body.setAttribute('data-theme', currentTheme);
    themeToggle.textContent = currentTheme === 'light' ? '🌙' : '☀️';
}

// Event listeners
themeToggle.addEventListener('click', toggleTheme);

// Initialize
loadVerses();
setInterval(updateDisplay, 1000); // Update every second 