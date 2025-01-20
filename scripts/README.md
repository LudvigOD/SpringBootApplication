# Backend Server User Guide

Welcome! This guide explains how to start and stop the backend server using a simple script. If you're responsible for managing the server, these instructions will help you keep things running smoothly.

## Starting and Stopping the Server

To interact with the server, you’ll use a command-line script that controls when the server is on or off.

### Basic Commands

- **Start the server**: Run the following command in the terminal:

  ```bash
  ./runner start
  ```

  This starts the backend server and creates a log file in the `logs` folder, where it saves important information.

- **Stop the server**: If you need to turn off the server, run:

  ```bash
  ./runner stop
  ```

  This safely shuts down the server and cleans up any temporary files.

- **Restart the server**: To restart the server, you can combine the two commands above:
  ```bash
  ./runner restart
  ```

### Log Management

The server creates a log file to keep track of its activity. Logs are automatically rotated, meaning that when the current log file reaches 50 MB, it’s renamed to `archive.log`, and a new log file starts. This keeps logs manageable and prevents them from taking up too much space.

## Troubleshooting

If you encounter any issues, here are some quick tips to help resolve them:

### "Server Already Running" Message

If you try to start the server and see a message saying it’s already running:

1. If you want to stop or restart the server, use the `stop` or `restart` commands.
1. If the stop command doesn’t work, you might need to troubleshoot further. Keep reading!
1. Make sure the server isn’t currently running by using:

   ```bash
   ps aux | grep 'backend.jar'
   ```

   This will show if there’s already a server process. If there is, you’ll need to stop it before starting a new one. See "Manually Stopping the Server" below.

1. If you’re sure the server isn’t running, check for a leftover PID file:
   ```bash
   ls backend-server.pid
   ```
   If the file is there, delete it and try starting the server again:
   ```bash
   rm backend-server.pid
   ./runner start
   ```

### Manually Stopping the Server

If the `stop` command doesn’t work, you can stop the server manually:

1. Check if the server process is running:

   ```bash
   ps aux | grep 'backend.jar'
   ```

   Example output:

   ```bash
    user 12345 0.0 0.0 12345 6789 ? S 12:34 0:00 java -jar backend.jar
           ^                                                  ^
          this is the PID                            this is the process
   ```

2. Identify the Process ID (PID) for the server (see above).

3. Kill the process using:
   ```bash
   kill -9 <PID>
   ```

Replace `<PID>` with the actual ID number of the process. Once it’s stopped, you should be able to start the server again normally.

---

If you follow these steps, you should be able to control the server without issues. Happy hosting!
