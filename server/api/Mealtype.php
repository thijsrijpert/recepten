<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Mealtype.php');
require_once(dirname(__FILE__,2) . '/database/Mealtype.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class Mealtype extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() {
      try{
          $this->model = new \model\Mealtype($_GET['name']);

          $mealtypestatement = new \database\Mealtype();
          $code = $mealtypestatement->insert($this->model);

          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          restore_error_handler();
      }
  }

  public function select(){
    try{
        $this->model = new \model\Mealtype();
        $queryBuilder = parent::buildQuery($this->model);

        //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $this->model->getVariables();
            foreach($arguments as $value){
                if($value[0] == 'name'){
                    $this->model->setName($value[2]);
                }
            }
        }

        $mealtypestatement = new \database\Mealtype($queryBuilder);
        $codeAndResult = $mealtypestatement->select($this->model);

        if($codeAndResult[0][1] == '00'){
            header('Content-Type: application/json');
            echo json_encode($codeAndResult[1][0]);
        }

        $code = substr($code, 0, 2);

        parent::setHttpCode($code);
    }catch(\PDOException $e){
        parent::setHttpCode($e->getCode());
    }catch(\exception\NullPointerException $e){
        header('HTTP/1.0 400 Bad Request');
        \var_dump($e);
        restore_error_handler();
    }
  }



  function error_handler($errno, $errstr, $errfile, $errline){
      if($errstr == 'Undefined index: name'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{
          restore_error_handler();
      }

  }

}

$mealtype = new Mealtype();
if(isset($_GET['name'])){
  $mealtype->insert();
}else{
  $mealtype->select();
}

 ?>
