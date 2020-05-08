<?php
namespace api;

require_once(dirname(__FILE__,2) . '/model/Review.php');
require_once(dirname(__FILE__,2) . '/database/Review.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Review extends Api implements CRInterface{
    public function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    public function insert(){
      try{
          $this->model = new \model\Review($_GET['title'], $_GET['rating'], $_GET['username'], $_GET['recipeId'], $_GET['description']);

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

    public function select(){
        $this->model = new Review();
        $queryBuilder = parent::buildQuery($this->model);

        //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $this->model->getVariables();
            foreach($arguments as $value){
                switch($value[0]){
                    case 'id':
                        $this->model->setId($value[2]);
                        break;
                    case 'title':
                        $this->model->setTitle($value[2]);
                        break;
                    case 'description':
                        $this->model->setDescription($value[2]);
                        break;
                    case 'rating':
                        $this->model->setRating($value[2]);
                        break;
                    case 'recipe_id':
                        $this->model->setRecipeId(new Recipe($value[2]));
                        break;
                    case 'username':
                        $this->model->setUsername(new User($value[2]));
                        break;
                    case 'review_date':
                        if(\count_chars($value[2]) == 8){
                            $date = \implode('-', \str_split('-', $value[2], 2));
                            $dateTime = new \DataTime::createFromFormat('d-m-Y', $date);
                            $this->model->setReviewDate($dateTime);
                        }else{
                            throw new InvalidRequestException("The inserted datetime is not of a valid format");
                        }
                    break;
                }
            }
        }

        $reviewStatement = new \database\Review($queryBuilder);
        $codeAndResult = $reviewStatement($this->model);

        if($codeAndResult[0][1] == '00'){
            header('Content-Type: application/json');
            echo json_encode($codeAndResult[1][0]);
        }

        $code = substr($codeAndResult[0][1], 0, 2);

        parent::setHttpCode($code);
    }

    function error_handler($errno, $errstr, $errfile, $errline){
        $errstr = substr($errstr, 17);
        if($errstr == 'title' || $errstr == 'rating' || $errstr == 'username' || $errstr == 'receptId' ){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }
}

$review = new Review();
if(isset($_GET['title'])){
    $this->insert();
}else{
    $this->select();
}
 ?>
