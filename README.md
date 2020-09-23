# findBrokenGlass

## About
This repo is a open source tool to check a connection status of URLs found from the input file, using Java. It returns "[code] url status" in each line.

<p align="center">
  <img src="./asset/defaultTool.png" alt="He's Dead, Jim" width="738">
</p>

## Getting Started
Please see the appropriate guide for your environment of choice:

### Installation / 
  1. Clone the repo
  
  ```bash
  git clone https://github.com/eunbeek/findBrokenGlass.git
  ```
## Usage
  
  This command returns help messange for the flag and the argument explanation.
  ```bash
  UrlCheck help
  ```
  
  You can type the input file name after tool name, then return the response code, URL and status in each line.
  This tool accepts multiple files and delimiters by space
  ```bash
  UrlCheck <fileName>
  ```

  ```bash
  UrlCheck <fileName1> <fileName2>
  ```
  
  You can type '-a' flag to allow checking for archived versions of URLs.
  ```bash
  UrlCheck -a <fileName>
  ```
   
  This '-s' flag change 'http' to 'https' in URls.  
  ```bash
  UrlCheck -s <fileName>
  ```

## Library 
https://github.com/java-native-access/jna
  
  


