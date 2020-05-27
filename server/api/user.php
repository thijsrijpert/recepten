<?php
namespace api;
require_once(dirname(__FILE__, 1) . '/Api.php');
require_once(dirname(__FILE__, 2) . '/database/User.php');
require_once(dirname(__FILE__, 2) . '/model/User.php');
class User extends Api implements CRInterface{
    private $model;
    private $chars;
    public function __construct(){
      parent::__construct();
      $chars = array_merge(range('A', 'Z'), range('0','9'));
      set_error_handler(array($this, 'error_handler'));
    }

    public function insert() {
        header('Content-Type: application/json');
        try{
            $hash = Hash::hashing_generate($_GET['password']);
            $token = generateToken();
            $this->model = new \model\User($_GET['username'], $hash, $token);

            $code = (new \database\User())->insert($model);

            parent::setHttpCode(substr($code, 0, 2));

            if($code == '00'){
                echo json_encode($this->model);
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
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Password cannot be used in the where clause");
                            die;
                            break;
                        case 'role':
                            header('HTTP/1.0 403 Forbidden');
                            echo json_encode("Role cannot be used in the where clause");
                            break;
                        case 'token':
                            $this->model->setToken($value[2]);
                            break;
                    }
                }
            }else{
                header('HTTP/1.0 403 Forbidden');
                echo \json_encode("The request should contain an where clause");
                die;
            }
            $queryBuilder = parent::buildQuery($this->model);

            $codeAndResult = (new \database\User($queryBuilder))->select($this->model);

            $code = substr($codeAndResult[0], 0, 2);

            if($code === '00' && count($codeAndResult[1][0]) === 1){
                echo json_encode($codeAndResult[1][0]);
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
        for($i = 0; $i <= 20; $i++){
            $token += (String) $chars[random_int(0,35)];
        }
        return $token;
    }
}
$user = new \api\User();
if(isset($_GET['username'])){
    $user->insert();
}else{
    //$user->select();
}

 ?>
