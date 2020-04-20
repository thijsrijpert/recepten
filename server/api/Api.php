<?php
  class Api {
      public function setHttpCode($sqlCode) : void{
          error_log($sqlCode);
          switch($sqlCode){
              case '00':
                  header('HTTP/1.0 200 OK');
                  break;
              case '01':
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
                  echo 'hi';
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
                  echo 'test';
                  header('HTTP/1.0 500 Internal Server error');
                  break;
          }
          die;
      }
  }
?>
