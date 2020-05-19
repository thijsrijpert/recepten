<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Review.php');
require_once(dirname(__FILE__,2) . '/model/User.php');
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
require_once(dirname(__FILE__,2) . '/database/Review.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/CRInterface.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Review extends Api implements CRInterface{
    public function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    public function insert() : void{
      try{
          //load all get parameters into the model
          $this->model = new \model\Review($_GET['title'], $_GET['rating'], new \model\User($_GET['username']), new \model\Recipe($_GET['recipeId']), $_GET['description']);
          //insert the model into the db
          $code = (new \database\Review())->insert($this->model);
          //get the class code from the full error code
          $code = substr($code, 0, 2);
          //set the http status code and die
          parent::setHttpCode($code);

      }catch(\PDOException $e){
          //set the http status code and die
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          //return the message for easy debug
          echo $e->getMessage();
          restore_error_handler();
      }
    }

    public function select() : void{
        try{
            //rebuild the get parameters in useful queries
            $this->model = new \model\Review();
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
                                $dateTime = DataTime::createFromFormat('dmY', $value[2]);
                                $this->model->setReviewDate($dateTime);
                            }else{
                                throw new InvalidRequestException("The inserted datetime is not of a valid format");
                            }
                        break;
                    }
                }
            }
            //execute the select statement and get the code and result object
            $codeAndResult = (new \database\Review($queryBuilder))->select($this->model);
            $code = substr($codeAndResult[0], 0, 2);

            //if the query was succesful return the data
            if($code === '00'){
                header('Content-Type: application/json');
                echo json_encode($codeAndResult[1][0]);
            }

            parent::setHttpCode($code);
          }catch(\PDOException $e){
              parent::setHttpCode($e->getCode());
          }catch(\exception\NullPointerException $e){
              header('HTTP/1.0 400 Bad Request');
              header('Content-Type: application/json');
              //return the message for easy debug
              echo json_encode($e->getMessage());
              restore_error_handler();
          }
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
    $review->insert();
}else{
    $review->select();
}
 ?>
