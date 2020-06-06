<?php
namespace database;
require_once(dirname(__FILE__, 2) . '/exception/ModelNullException.php');
require_once(dirname(__FILE__, 2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__, 2) . '/database/CRInterface.php');
require_once(dirname(__FILE__, 2) . '/database/Database.php');
require_once(dirname(__FILE__, 2) . '/model/User.php');
require_once(dirname(__FILE__, 2) . '/database/CRUD.php');
class User extends CRUD implements CRInterface{
    function __construct(QueryBuilderParent ...$query){
        parent::__construct($query);

        $sql = "INSERT INTO User (username, password, token, salt, iteration) VALUES (:username, :password, :token, :salt, :iteration )";
        $this->stmt = Database::getConnection()->prepare($sql);
    }

    public function insert(\model\Model $model) : String{
        try{
            $username = $model->getUsername();
            $password = $model->getPassword();
            $token = $model->getToken();
            $salt = $model->getSalt();
            $iteration = $model->getIteration();
        }catch(\exception\ModelNullException $e){
            throw new \exception\NullPointerException($e);
        }
        $this->stmt->bindParam(':username', $username);
        $this->stmt->bindParam(':password', $password);
        $this->stmt->bindParam(':token', $token);
        $this->stmt->bindParam(':salt', $salt);
        $this->stmt->bindParam(':iteration', $iteration);

        $this->stmt->execute();

        return $this->stmt->errorCode();
    }

    public function select(\model\Model $model) : array {
        try{
            $this->select[0]->bindParam(':username', $model->getUsername());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':password', $model->getPassword());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':token', $model->getToken());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':role', $model->getRole());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':salt', $model->getSalt());
        }catch(\exception\ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':iteration', $model->getIteration());
        }catch(\exception\ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\User');

        return array($this->select[0]->errorCode(), array($results));
    }

    function error_handler($errno, $errstr, $errfile, $errline){

    }
}
?>
