const { contextBridge } = require('electron');
const { spawn, exec } = require('child_process');
const path = require('path');

const appName = 'ali-dbhub-server-start.jar';
contextBridge.exposeInMainWorld('myAPI', {
  startServerForSpawn: () => {
    const path1 = path.join('./electron', `app/${appName}`);
    console.log(path1)
    const ls = spawn('./electron/jre/Contents/Home/bin/java', ['-jar', path1]);
    ls.stdout.on('data', (buffer) => {
      console.log(buffer.toString('utf8'));
      const data = buffer.toString('utf8');
      if (data.toString().indexOf('Started Application') !== -1) {
        console.log('load success');
        // TODO: 
        // window.location.href = 'http://localhost:10824';
      }
    });
    ls.stderr.on('data', (data) => {
      console.error(`stderr: ${data}`);
      alert('启动服务异常');
    });
    ls.on('close', (code) => {
      console.log(`child process exited with code ${code}`);
    });
  },
  startServerForbat: () => {
    const bat = spawn(path.join(__dirname, 'my.bat'));
    bat.stdout.on('data', (data) => {
      console.log(data.toString());
      if (data.toString().indexOf('Started') !== -1) {
        window.location.href = 'http://127.0.0.1:8080';
      }
    });
    bat.stderr.on('data', (data) => {
      console.error(data.toString());
    });
    bat.on('exit', (code) => {
      console.log(`Child exited with code ${code}`);
    });
  },
});
