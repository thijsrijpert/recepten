<?php
namespace api;

require_once(dirname(__FILE__, 2) . '/database/Query.php');
require_once(dirname(__FILE__, 2) . '/database/QueryBuilder.php');
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
  class Api {
      public function __construct(){
        if(!defined('ALLOWED_COMPARISON')){
            define('ALLOWED_COMPARISON', ['>' => 'gr', '=' => 'eq', '<' => 'sm']);
            define('ALLOWED_ORDER', ['desc' => 'desc', 'asc' => 'asc']);
        }

        if(!defined('TESING')){
            define('TESTING', false);
        }

        ini_set('display_startup_errors', 1);
        ini_set('display_errors', 1);
        error_reporting(E_ALL | E_STRICT);
      }
      public function setHttpCode($code) : void{
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
                  break;
              case '23':
                  //Constraint vialation
                  header('HTTP/1.0 400 Bad Request');
                  break;
              default:
                  header('HTTP/1.0 500 Internal Server error');
                  break;
          }
          die;
      }

      public function buildQuery(\model\Model $entity) : \database\QueryBuilder{
          $query = new \database\Query($entity);

          if(isset($_GET['select'])){
              $arguments = $this->rebuildArguments($_GET['select']);
              if(!$query->setSelectArguments($arguments)){
                  throw new \exception\NullPointerException("Select has invalid argument");
              }
          }else{
              if(!$query->setSelectArguments()){
                  throw new \exception\NullPointerException("Select has invalid argument");
              }
          }

          if(isset($_GET['where'])){
              $arguments = $this->rebuildArguments($_GET['where']);
              if(!$query->setWhereArguments($arguments)){
                  throw new \exception\NullPointerException("Where has invalid argument");
              }
          }

          if(isset($_GET['order'])){
              $arguments = $this->rebuildArguments($_GET['order']);
              if(!$query->setOrderArguments($arguments)){
                  throw new \exception\NullPointerException("Order has invalid argument");
              }
          }

          $queryBuilder = new \database\QueryBuilder($query);
          $queryBuilder->generateSql();
          return $queryBuilder;
      }

      public function rebuildArguments(String $get_parameter) : array{
          // $parameterFull = urlencode($get_parameter);
          // $parameterFull = str_replace("+", "%2B", $parameterFull);
          // $parameterFull = urldecode($parameterFull);
          $parameters = \explode('.', $get_parameter);

          foreach($parameters as $key => $value){
              $parameters[$key] = \explode('-', $value, 3);
          }

          return $parameters;
      }
  }
?>
