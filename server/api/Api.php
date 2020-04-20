<?php
  class Api {
      public function __construct(){
        ini_set('display_startup_errors', 1);
        ini_set('display_errors', 1);
        error_reporting(E_ALL | E_STRICT);
      }
      public function setHttpCode($code) : void{
          error_log($sqlCode);
          switch($sqlCode){
              case '00':
                  //SQL query succesful
                  header('HTTP/1.0 200 OK');
                  break;
              case '01':
                  //just a warning continue
                  header('HTTP/1.0 200 OK');
                  break;
              case '02':
                  header('HTTP/1.0 404 Not Found');
                  break;
              case '07':
              echo 'hallo';
                  header('HTTP/1.0 500 Internal Server Error');
                  break;
              case 2002:
                  header('HTTP/1.0 503 Service Unavailable');
                  break;
              case '08':
                  header('HTTP/1.0 503 Service Unavailable');
                  break;
              case '22':
                  header('HTTP/1.0 400 Bad Request');
                  break;
              case '23':
                  header('HTTP/1.0 400 Bad Request');
                  break;
              default:
                  header('HTTP/1.0 500 Internal Server error');
                  break;
          }
          die;
      }
  }
?>
