<?php
namespace api;
  class Api {
      public function __construct(){
        define('ALLOWED_COMPARISON', ['gr', 'eq', 'sm']);
        define('ALLOWED_DATABASE_COMPARISON', ['gr', 'eq', 'sm']);
        ini_set('display_startup_errors', 1);
        ini_set('display_errors', 1);
        error_reporting(E_ALL | E_STRICT);
      }
      public function setHttpCode($code) : void{
          error_log($code);
          echo $code;
          switch($code){
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
                  //Data Exception
                  header('HTTP/1.0 400 Bad Request');
                  echo $sqlCode;
                  break;
              case '23':
                  //Constraint vialation
                  header('HTTP/1.0 400 Bad Request');
                  echo $sqlCode;
                  break;
              default:
                  header('HTTP/1.0 500 Internal Server error');
                  break;
          }
          die;
      }
  }
?>
