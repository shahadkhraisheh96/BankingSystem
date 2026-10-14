const { app, BrowserWindow, shell } = require('electron');
const path = require('path');
const http = require('http');

let mainWindow;

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 1300,
    height: 860,
    minWidth: 1050,
    minHeight: 700,
    backgroundColor: '#0a0f1d',
    title: 'AeroBank Enterprise Desktop',
    webPreferences: {
      nodeIntegration: false,
      contextIsolation: true
    }
  });

  // Remove default menu bar for modern desktop look
  mainWindow.setMenuBarVisibility(false);

  // Check if Angular dev server is running on localhost:4200
  const devUrl = 'http://localhost:4200';
  const req = http.get(devUrl, () => {
    mainWindow.loadURL(devUrl);
  });

  req.on('error', () => {
    // Fallback to built distribution files
    const indexPath = path.join(__dirname, '../dist/frontend/browser/index.html');
    mainWindow.loadFile(indexPath).catch(() => {
      // If build not found yet, show friendly guidance
      mainWindow.loadURL(devUrl);
    });
  });

  // Open external links in default system browser
  mainWindow.webContents.setWindowOpenHandler(({ url }) => {
    shell.openExternal(url);
    return { action: 'deny' };
  });

  mainWindow.on('closed', () => {
    mainWindow = null;
  });
}

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});
