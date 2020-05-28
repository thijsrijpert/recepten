<?php
namespace api;
require_once(dirname(__FILE__,2) . '/model/Review.php');
require_once(dirname(__FILE__,2) . '/model/User.php');
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
require_once(dirname(__FILE__,2) . '/database/Review.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/CRUInterface.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Review extends Api implements CRUInterface{
    public function __construct(){
        parent::__construct();
        set_error_handler(array($this, 'error_handler'));
    }

    public function insert() {
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

    public function select() {
        try{
            //rebuild the get parameters in useful queries
            $this->model = $this->getWhereModel();
            $queryBuilder = parent::buildQuery($this->model);


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

    function update(){
        try{
            $modelOld = $this->getWhereModel();
            $modelNew = $this->getSetModel();

            $queryBuilderSelect = parent::buildQuery($modelOld);
            $queryBuilderUpdate = parent::buildUpdate($modelNew);

            $statement = new \database\Review($queryBuilderSelect, $queryBuilderUpdate);

            $result = $statement->select($modelOld);

            if(count($result[1][0]) == 1){
                $resultUpdate = $statement->update($modelNew, $modelOld);
                parent::setHttpCode($resultUpdate);
            }else{
                throw new \exception\NullPointerException("The request changed more than one record, please change your where scope");
            }

        }catch(\PDOException $e){
            parent::setHttpCode($e->getCode());
        }catch(\exception\NullPointerException $e){
            header('HTTP/1.0 400 Bad Request');
            //set the datatype to json for consistancy with all select query's
            header('Content-Type: application/json');
            //return the error code for easy debug
            echo json_encode($e->getMessage());
            restore_error_handler();
        }

    }
    function getWhereModel() : \model\Model{
      $model = new \model\Review();
        //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
        if(null != $_GET['where']){
            $arguments = parent::rebuildArguments($_GET['where']);
            $approvedArguments = $model->getVariables();
            $model =  $this->getModel($model, $arguments, $approvedArguments);
        }
        return $model;
    }

    function getSetModel() : \model\Model {
      //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
      if(null != $_GET['set']){
          $model = new \model\Review();
          $arguments = parent::rebuildArguments($_GET['set']);
          $approvedArguments = $model->getUpdateVariables();
          return $this->getModel($model, $arguments, $approvedArguments);
      }
    }

    function getModel(\model\Model $model, array $arguments, array $approvedArguments) : \model\Model{
        foreach($arguments as $value){
            switch($value[0]){
                case 'id':
                    $model->setId(end($value));
                    break;
                case 'title':
                    $model->setTitle(end($value));
                    break;
                case 'description':
                    $model->setDescription(end($value));
                    break;
                case 'rating':
                    $model->setRating(end($value));
                    break;
                case 'recipe_id':
                    $model->setRecipeId(new \model\Recipe(end($value)));
                    break;
                case 'username':
                    $model->setUsername(new \model\User(end($value)));
                    break;
                case 'review_date':
                    if(\strlen(end($value)) == 8){
                        $dateTime = \DateTime::createFromFormat('dmY', end($value));
                        $model->setReviewDate($dateTime);
                    }else{
                        throw new \exception\NullPointerException("The inserted datetime is not of a valid format");
                    }
                break;
            }
        }
        return $model;
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
}else if(isset($_GET['set'])){
    $review->update();
}else{
    $review->select();
}
 ?>
