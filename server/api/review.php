<?php
namespace api;

require_once(dirname(__FILE__,2) . '/model/Review.php');
require_once(dirname(__FILE__,2) . '/database/Review.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Review extends Api {
    public function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    public function insert(){
      try{
          $this->model = new \model\Review($_GET['title'], $_GET['description'], $_GET['rating'], $_GET['username']);

          $religieStatement = new \database\Review();
          $code = $religieStatement->insert($this->model);

          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          restore_error_handler();
      }
    }

    function error_handler($errno, $errstr, $errfile, $errline){
        $errstr = substr($errstr, 17);
        if($errstr == 'title' || $errstr == 'description' || $errstr == 'rating' || $errstr == 'username'){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }
}

$review = new Review();
$review->insert();
 ?>
