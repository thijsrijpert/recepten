<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/RecipeIngredient.php');
require_once(dirname(__FILE__,2) . '/database/RecipeIngredient.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');
class ReceptIngredient extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert(){
      try{
          $this->model = new model\Recipe_Ingredient($_GET['recept_id'], $_GET['ingredient_name']);

          $recipe_ingredientStatement = new database\Recipe_Ingredient();
          $code = $recipe_ingredientStatement->insert($this->model);

          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
      }
  }

  public function select(){
    try{
        $this->model = new \model\Recipe_Ingredient();
        $queryBuilder = parent::buildQuery($this->model);


        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $this->model->getVariables();
            foreach($arguments as $value){
                if($value[0] == 'recipe_id'){
                    $this->model->setRecipeId($value[2]);
                }else if($value[0] == 'ingredient_name'){
                    $this->model->setIngredientName($value[2]);
                }
            }
        }

        $recipe_ingredientStatement = new \database\RecipeIngredient($queryBuilder);
        $codeAndResult = $recipe_ingredientStatement->select($this->model);

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
      if($errstr == 'Undefined index: recipe_id' || $errstr == 'Undefined index: ingredient_name'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{

          restore_error_handler();
      }
  }
}

$receptIngredient = new ReceptIngredient();
if(isset($_GET['recipe_name'])){
    $receptIngredient->insert();
}else{
    $receptIngredient->select();
}

 ?>
