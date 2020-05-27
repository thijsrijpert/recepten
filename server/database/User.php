<?php
namespace database;
class User extends CRUD implements CRInterface{
    function __construct($query = null){
        parent::__construct($query);

        $sql = "INSERT INTO User (username, password, token, role ) VALUES (:username, :password, :token, :role)";
        $this->stmt = Database::getConnection()->prepare($sql);
    }

    public function insert(\model\Model $model) : String{
        try{
            $username = $model->getUsername();
            $password = $model->getPassword();
            $token = $model->getToken();
            $role = $model->getRole();
        }catch(ModelNullException $e){
            throw new NullPointerException($e);
        }

        $this->stmt->bindParam(':username', $username);
        $this->stmt->bindParam(':password', $password);
        $this->stmt->bindParam(':token', $token);
        $this->stmt->bindParam(':role', $role);
        $this->stmt->execute();

        return $this->stmt->errorCode();
    }

    public function select(\model\Model $model) : array {
        try{
            $this->select[0]->bindParam(':username', $model->getUsername());
        }catch(ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':password', $model->getPassword());
        }catch(ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':token', $model->getToken());
        }catch(ModelNullException $e){}

        try{
            $this->select[0]->bindParam(':role', $model->getRole());
        }catch(ModelNullException $e){}

        $this->select[0]->execute();

        $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\User');

        return array($this->select[0]->errorCode(), array($results));
    }

    function error_handler($errno, $errstr, $errfile, $errline){

    }
}
?>
