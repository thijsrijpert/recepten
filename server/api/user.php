<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__, 1) . '/Api.php');
require_once(dirname(__FILE__, 2) . '/database/User.php');
require_once(dirname(__FILE__, 2) . '/model/User.php');
require_once(dirname(__FILE__, 1) . '/CRInterface.php');
require_once(dirname(__FILE__, 1) . '/Hashing.php');
require_once(dirname(__FILE__, 2) . '/exception/ModelNullException.php');
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
class User extends Api implements CRInterface{
    private $model;
    private $chars;
    public function __construct(){
      parent::__construct();
      $this->chars = array_merge(range('A', 'Z'), range('0','9'));
      set_error_handler(array($this, 'error_handler'));
    }

    public function insert() {
        header('Content-Type: application/json');
        try{
            $this->model = new \model\User($_GET['username'], $_GET['password'], $this->generateToken());
            $this->model = Hash::hashing_generate($this->model);
            $statement = new \database\User();
            $code = $statement->insert($this->model);
            if(substr($code, 0, 2) == '00'){
                echo json_encode($this->model);
                parent::setHttpCode(substr($code, 0, 2));
            }
        }catch(PDOException $e) {
            parent::setHttpCode($e->getCode());
        }catch(NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          echo $e->getMessage();
          restore_error_handler();
        }
    }

    public function select() {
        header('Content-Type: application/json');
        try{
            $this->model = new \model\User();
            //I don't know how to get the decoded arguments to the database, so I will call rebuildArguments again
            if(null != $_GET['where']){
                $arguments = parent::rebuildArguments($_GET['where']);
                $approvedArguments = $this->model->getVariables();
                foreach($arguments as $value){
                    switch($value[0]){
                        case 'username':
                            $this->model->setUsername($value[2]);
                            break;
                        case 'password':
                            //password cannot be in the where clause so it cannot be set right here
                            $password = $value[2];
                            break;
                        case 'role':
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Role cannot be used in the where clause");
                            die;
                            break;
                        case 'salt':
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Salt cannot be used in the where clause");
                            die;
                            break;
                        case 'iteration':
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Iteration cannot be used in the where clause");
                            die;
                            break;
                        case 'token':
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Token cannot be used in the where clause, if the entity is user");
                            die;
                            break;
                    }
                }
            }else{
                header('HTTP/1.0 400 Bad Request');
                echo \json_encode("The request should contain an where clause");
                die;
            }
            $queryBuilder = parent::buildQuery($this->model);

            $codeAndResult = (new \database\User($queryBuilder))->select($this->model);

            $this->model->setPassword($password);


            $code = substr($codeAndResult[0], 0, 2);

            if($code === '00' && count($codeAndResult[1][0]) === 1){
                if(isset($password)){
                    $this->model->setPassword($password);
                    if(Hash::hashing_verify($this->model, $codeAndResult[1][0][0])){
                        echo json_encode($codeAndResult[1][0]);
                        die;
                    }else{
                        header('HTTP/1.0 403 Forbidden');
                        echo json_encode("The password is invalid");
                        die;
                    }
                }else{
                  header('HTTP/1.0 400 Bad Request');
                  echo json_encode("You must pass a password otherwise the request is invalid");
                  die;
                }

            }else if ($codeAndResult[1][0] > 1){
                header('HTTP/1.0 403 Forbidden');
                echo json_encode("The result set is too large to be valid, requests should be limited to one user");
                die;
            }

            parent::setHttpCode($code);
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

    function error_handler($errno, $errstr, $errfile, $errline){
        $errstr = substr($errstr, 17);
        if($errstr == 'password' || $errstr == 'username'){
            throw new \exception\NullPointerException("Get value isn't passed");
        }else{
            restore_error_handler();
        }
    }

    public function generateToken() : String {
        $token = "";
        for($i = 0; $i <= 15; $i++){
            $token .= (String) $this->chars[random_int(0,35)];
        }

        return $token;
    }
}
$user = new \api\User();
if(isset($_GET['username'])){
    $user->insert();
}else{
    $user->select();
}

 ?>
