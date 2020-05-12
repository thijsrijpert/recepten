<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Ingredient.php');
require_once(dirname(__FILE__,2) . '/database/Ingredient.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class Ingredient extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() : void{
    try{
      $this->model = new \model\Ingredient($_GET['name'], $_GET['description'], $_GET['is_approved'], $_GET['username']);
      \var_dump($this->model);
      $ingredientStatement = new \database\Ingredient();
      $code = $ingredientStatement->insert($this->model);

      $code = substr($code, 0, 2);
      parent::setHttpCode($code);

    }catch(\PDOException $e){
        parent::setHttpCode($e->getCode());
    }catch(exception\NullPointerException $e){
        header('HTTP/1.0 400 Bad Request');
        \var_dump($e);
        restore_error_handler();
    }
  }

  public function select(){
    try{
        $this->model = new \model\Ingredient();
        $queryBuilder = parent::buildQuery($this->model);


        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $this->model->getVariables();
            foreach($arguments as $value){
                if($value[0] == 'username'){
                    $this->model->setUsername($value[2]);
                }else if($value[0] == 'is_approved'){
                    $this->model->setIs_approved($value[2]);
                }else if($value[0] == 'description'){
                  $this->model->setDescription($value[2]);
              }else if($value[0] == 'name'){
                $this->model->setName($value[2]);
            }
            }
        }

        $ingredientStatement = new \database\Ingredient($queryBuilder);
        $codeAndResult = $ingredientStatement->select($this->model);

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
        restore_error_handler();
    }
  }

  function error_handler($errno, $errstr, $errfile, $errline){
      if($errstr == 'Undefined index: name' || $errstr == 'Undefined index: description' || $errstr == 'Undefined index: is_approved' || $errstr == 'Undefined index: username'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{
          restore_error_handler();
      }
  }
}

$ingredient = new Ingredient();
if(isset($_GET['name'])){
  $ingredient->insert();
}else{

}
?>
