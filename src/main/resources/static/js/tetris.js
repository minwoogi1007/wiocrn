// 게임 상수
const COLS = 10;
const ROWS = 20;
const BLOCK_SIZE = 30;
const SHAPES = [
    [],
    [[0, 0, 0, 0], [1, 1, 1, 1], [0, 0, 0, 0], [0, 0, 0, 0]],
    [[2, 0, 0], [2, 2, 2], [0, 0, 0]],
    [[0, 0, 3], [3, 3, 3], [0, 0, 0]],
    [[4, 4], [4, 4]],
    [[0, 5, 5], [5, 5, 0], [0, 0, 0]],
    [[0, 6, 0], [6, 6, 6], [0, 0, 0]],
    [[7, 7, 0], [0, 7, 7], [0, 0, 0]]
];
const COLORS = [
    null,
    '#FF0D72', '#0DC2FF', '#0DFF72',
    '#F538FF', '#FF8E0D', '#FFE138', '#3877FF'
];

// 게임 변수
let canvas, ctx, canvasNext, ctxNext;
let gameLoop;
let board;
let piece, nextPiece;
let dropStart;
let dropInterval = 1000;
let score = 0;
let level = 1;
let highScore = 0;
let isPaused = false;
let isGameOver = false;

// 초기화
function init() {
    canvas = document.getElementById('tetris');
    ctx = canvas.getContext('2d');
    canvasNext = document.getElementById('next');
    ctxNext = canvasNext.getContext('2d');

    canvas.width = COLS * BLOCK_SIZE;
    canvas.height = ROWS * BLOCK_SIZE;
    canvasNext.width = 4 * BLOCK_SIZE;
    canvasNext.height = 4 * BLOCK_SIZE;

    ctx.scale(BLOCK_SIZE, BLOCK_SIZE);
    ctxNext.scale(BLOCK_SIZE, BLOCK_SIZE);

    board = createBoard();
    addListeners();
    resetGame();
}

// 보드 생성
function createBoard() {
    return Array.from({length: ROWS}, () => Array(COLS).fill(0));
}

// 새 조각 생성
function getNewPiece() {
    const shapeIndex = Math.floor(Math.random() * (SHAPES.length - 1)) + 1;
    return {
        shape: SHAPES[shapeIndex],
        color: COLORS[shapeIndex],
        x: Math.floor(COLS / 2) - 2,
        y: 0
    };
}

// 그리기
function draw() {
    ctx.fillStyle = '#F0F0F0';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    drawBoard();
    drawPiece(ctx, piece);
    drawGrid();
}

// 보드 그리기
function drawBoard() {
    board.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value > 0) {
                ctx.fillStyle = COLORS[value];
                ctx.fillRect(x, y, 1, 1);
            }
        });
    });
}

// 격자 그리기
function drawGrid() {
    ctx.strokeStyle = '#CCCCCC';
    ctx.lineWidth = 0.02;
    for (let i = 0; i <= COLS; i++) {
        ctx.beginPath();
        ctx.moveTo(i, 0);
        ctx.lineTo(i, ROWS);
        ctx.stroke();
    }
    for (let i = 0; i <= ROWS; i++) {
        ctx.beginPath();
        ctx.moveTo(0, i);
        ctx.lineTo(COLS, i);
        ctx.stroke();
    }
}

// 조각 그리기
function drawPiece(ctx, piece) {
    piece.shape.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value > 0) {
                ctx.fillStyle = piece.color;
                ctx.fillRect(piece.x + x, piece.y + y, 1, 1);
            }
        });
    });
}

// 다음 조각 그리기
function drawNextPiece() {
    ctxNext.fillStyle = '#F0F0F0';
    ctxNext.fillRect(0, 0, canvasNext.width, canvasNext.height);
    drawPiece(ctxNext, {
        ...nextPiece,
        x: 0,
        y: 0
    });
}

// 충돌 검사
function collision(x, y, shape) {
    return shape.some((row, dy) => {
        return row.some((value, dx) => {
            let newX = x + dx;
            let newY = y + dy;
            return (
                value !== 0 &&
                (newX < 0 || newX >= COLS || newY >= ROWS || (newY >= 0 && board[newY][newX] !== 0))
            );
        });
    });
}

// 키 입력 처리
function handleKeyPress(event) {
    if (isGameOver) return;
    if (isPaused && event.keyCode !== 80) return;  // 80 is 'p'

    switch(event.keyCode) {
        case 37: // 왼쪽
            if (!collision(piece.x - 1, piece.y, piece.shape)) {
                piece.x--;
            }
            break;
        case 39: // 오른쪽
            if (!collision(piece.x + 1, piece.y, piece.shape)) {
                piece.x++;
            }
            break;
        case 40: // 아래
            if (!collision(piece.x, piece.y + 1, piece.shape)) {
                piece.y++;
            }
            break;
        case 38: // 위 (회전)
            const rotated = rotate(piece.shape);
            if (!collision(piece.x, piece.y, rotated)) {
                piece.shape = rotated;
            }
            break;
        case 32: // 스페이스바
            hardDrop();
            break;
        case 80: // 'p' key
            togglePause();
            break;
    }
    draw();
}

// 회전
function rotate(shape) {
    return shape[0].map((_, i) => shape.map(row => row[i])).reverse();
}

// 하드 드롭
function hardDrop() {
    while (!collision(piece.x, piece.y + 1, piece.shape)) {
        piece.y++;
    }
    lockPiece();
}

// 조각 고정
function lockPiece() {
    piece.shape.forEach((row, y) => {
        row.forEach((value, x) => {
            if (value > 0) {
                board[piece.y + y][piece.x + x] = value;
            }
        });
    });
    removeRows();
    piece = nextPiece;
    nextPiece = getNewPiece();
    drawNextPiece();
    if (collision(piece.x, piece.y, piece.shape)) {
        gameOver();
    }
}

// 행 제거
function removeRows() {
    let rowsCleared = 0;
    outer: for (let y = ROWS - 1; y >= 0; y--) {
        for (let x = 0; x < COLS; x++) {
            if (board[y][x] === 0) continue outer;
        }
        const row = board.splice(y, 1)[0].fill(0);
        board.unshift(row);
        y++;
        rowsCleared++;
    }
    if (rowsCleared > 0) {
        score += rowsCleared * 10 * level;
        level = Math.floor(score / 100) + 1;
        dropInterval = Math.max(200, 1000 - (level - 1) * 100);  // 최소 200ms
        updateScore();
    }
}

// 드롭
function drop() {
    const now = Date.now();
    const delta = now - dropStart;

    if (delta > dropInterval) {
        if (!collision(piece.x, piece.y + 1, piece.shape)) {
            piece.y++;
        } else {
            lockPiece();
        }
        dropStart = now;
    }

    if (!isGameOver && !isPaused) {
        draw();
        requestAnimationFrame(drop);
    }
}

// 점수 업데이트
function updateScore() {
    document.getElementById('score').textContent = score;
    document.getElementById('level').textContent = level;
    if (score > highScore) {
        highScore = score;
        localStorage.setItem('tetrisHighScore', highScore);
    }
    document.getElementById('highScore').textContent = highScore;
}

// 게임 오버
function gameOver() {
    isGameOver = true;
    cancelAnimationFrame(gameLoop);
    ctx.fillStyle = 'rgba(0, 0, 0, 0.75)';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = '#FFFFFF';
    ctx.font = '1px Arial';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.fillText('GAME OVER', COLS / 2, ROWS / 2);
    document.getElementById('start-pause').textContent = 'Start New Game';
}

// 일시정지 토글
function togglePause() {
    isPaused = !isPaused;
    if (isPaused) {
        cancelAnimationFrame(gameLoop);
        ctx.fillStyle = 'rgba(0, 0, 0, 0.75)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = '#FFFFFF';
        ctx.font = '1px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('PAUSED', COLS / 2, ROWS / 2);
    } else {
        dropStart = Date.now();
        gameLoop = requestAnimationFrame(drop);
    }
    document.getElementById('start-pause').textContent = isPaused ? 'Resume' : 'Pause';
}

// 게임 리셋
function resetGame() {
    cancelAnimationFrame(gameLoop);
    board = createBoard();
    score = 0;
    level = 1;
    dropInterval = 1000;
    isGameOver = false;
    isPaused = false;
    piece = getNewPiece();
    nextPiece = getNewPiece();
    dropStart = Date.now();
    updateScore();
    drawNextPiece();
    gameLoop = requestAnimationFrame(drop);
    document.getElementById('start-pause').textContent = 'Pause';
}

// 이벤트 리스너 추가
function addListeners() {
    document.addEventListener('keydown', handleKeyPress);
    document.getElementById('start-pause').addEventListener('click', function() {
        if (isGameOver) {
            resetGame();
        } else {
            togglePause();
        }
    });
}

// 게임 시작
init();