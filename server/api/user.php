<?php
namespace api;
class User extends Api implements CRInterface{
    private $model;
    public function __construct(){
      parent::__construct();

      set_error_handler(array($this, 'error_handler'));
    }

    public function insert() : void{
        try{
            $hash = Hash::hashing_generate($_GET['password']);
            $this->model = new \model\User($_GET['username'], $hash, );

            $code = (new \database\User())->insert($model);

            parent::setHttpCode(substr($code, 0, 2));
        }catch(PDOException $e) {
            parent::setHttpCode($e->getCode());
        }catch(NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          echo $e->getMessage();
          restore_error_handler();
        }
    }

    public function select() : void{

    }
}
$user = new User();
if(isset($_GET['username'])){
    $user->insert();
}

 ?>
