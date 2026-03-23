# Directories

A simple Java GUI app that allows the user to navigate the computer’s file system. The GUI is built with the AWT library.

## Features
- Navigate folders and files via a GUI
- Basic file-system browsing
- Displays the root and all subfolders of a folder as an indented list

## Color meaning
- red — root folder
- green — a folder that is empty *or* requires elevated privileges to open (can’t be expanded further)
- black — a non-empty, accessible folder
- blue — a file (can’t be expanded further)

### Info
Double-click a folder name to expand it. If the folder is accessible and not empty, its subfolders and files will appear below it with indentation.

This program does **not** modify the file system (it can’t add or delete files); it only displays the existing structure.
