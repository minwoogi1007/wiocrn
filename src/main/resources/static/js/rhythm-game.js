// 게임 변수
let score = 0;
let combo = 0;
let level = 1;
let missedNotes = 0;
const maxMissedNotes = 10;
const lanes = [0, 1, 2, 3];
const keys = ['D', 'F', 'J', 'K'];
let notes = [];
let animationId;
let isGameOver = false;

// 오디오 컨텍스트 및 사운드
const audioContext = new (window.AudioContext || window.webkitAudioContext)();
const noteFrequencies = [261.63, 293.66, 329.63, 349.23]; // C4, D4, E4, F4

// 게임 컨테이너
const gameContainer = document.getElementById('game-container');
const startButton = document.getElementById('start-button');
const gameOverDisplay = document.getElementById('game-over');

// 노트 생성
function createNote() {
    const lane = Math.floor(Math.random() * 4);
    const note = document.createElement('div');
    note.className = 'note';
    note.style.left = `${lane * 100}px`;
    note.style.top = '0px';
    gameContainer.appendChild(note);
    notes.push({ element: note, lane: lane, position: 0 });
}

// 노트 이동
function moveNotes() {
    notes.forEach((note, index) => {
        note.position += 2 + level * 0.5; // 레벨에 따라 속도 증가
        note.element.style.top = `${note.position}px`;

        if (note.position > 580) {
            gameContainer.removeChild(note.element);
            notes.splice(index, 1);
            missNote();
        }
    });
}

// 키 입력 처리
document.addEventListener('keydown', (event) => {
    if (isGameOver) return;

    const key = event.key.toUpperCase();
    const laneIndex = keys.indexOf(key);

    if (laneIndex !== -1) {
        checkHit(laneIndex);
        playNote(laneIndex);
        flashKeyIndicator(laneIndex);
    }
});

// 히트 체크
function checkHit(laneIndex) {
    const hitNote = notes.find(note => note.lane === laneIndex && note.position >= 540 && note.position <= 580);

    if (hitNote) {
        gameContainer.removeChild(hitNote.element);
        notes = notes.filter(note => note !== hitNote);
        updateScore(100 + combo * 10);
        increaseCombo();
    } else {
        missNote();
    }
}

// 콤보 증가
function increaseCombo() {
    combo++;
    updateComboDisplay();
}

// 노트 놓침
function missNote() {
    combo = 0;
    updateComboDisplay();
    updateScore(-50);
    missedNotes++;
    if (missedNotes >= maxMissedNotes) {
        gameOver();
    }
}

// 점수 업데이트
function updateScore(points) {
    score += points;
    document.getElementById('score-value').textContent = score;
}

// 콤보 표시 업데이트
function updateComboDisplay() {
    document.getElementById('combo-value').textContent = combo;
}

// 레벨 업데이트
function updateLevel() {
    level = Math.floor(score / 1000) + 1;
    document.getElementById('level-value').textContent = level;
}

// 사운드 재생
function playNote(laneIndex) {
    const oscillator = audioContext.createOscillator();
    oscillator.type = 'sine';
    oscillator.frequency.setValueAtTime(noteFrequencies[laneIndex], audioContext.currentTime);

    const gainNode = audioContext.createGain();
    gainNode.gain.setValueAtTime(0.5, audioContext.currentTime);
    gainNode.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 0.5);

    oscillator.connect(gainNode);
    gainNode.connect(audioContext.destination);

    oscillator.start();
    oscillator.stop(audioContext.currentTime + 0.5);
}

// 키 표시기 플래시
function flashKeyIndicator(laneIndex) {
    const keyIndicator = document.querySelectorAll('.key-indicator')[laneIndex];
    keyIndicator.style.backgroundColor = 'white';
    setTimeout(() => {
        keyIndicator.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
    }, 100);
}

// 게임 루프
function runGameLoop() {
    if (Math.random() < 0.03 + (level * 0.01)) { // 레벨에 따라 노트 생성 확률 증가
        createNote();
    }
    moveNotes();
    updateLevel();
    if (!isGameOver) {
        animationId = requestAnimationFrame(runGameLoop);
    }
}

// 게임 시작
function startGame() {
    // 기존 노트 모두 제거
    notes.forEach(note => {
        if (note.element && note.element.parentNode) {
            note.element.parentNode.removeChild(note.element);
        }
    });
    notes = [];

    // 게임 변수 초기화
    score = 0;
    combo = 0;
    level = 1;
    missedNotes = 0;
    isGameOver = false;

    // UI 업데이트
    updateScore(0);
    updateComboDisplay();
    updateLevel();
    gameOverDisplay.style.display = 'none';
    startButton.style.display = 'none';

    // 애니메이션 프레임 취소 및 재시작
    if (animationId) {
        cancelAnimationFrame(animationId);
    }
    runGameLoop();
}

// 게임 오버
function gameOver() {
    isGameOver = true;
    cancelAnimationFrame(animationId);
    gameOverDisplay.style.display = 'block';
    startButton.style.display = 'block';
    startButton.textContent = 'Restart Game';
}

// 시작 버튼 이벤트 리스너
startButton.addEventListener('click', startGame);