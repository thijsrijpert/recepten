<?php
namespace Api;

class Hash{

  private $salt;
  private final $secretnum = 10;
  private $hash;
  private $iterration;

function __construct(int $salt, int $iterration){
  $this->salt = $salt;
  $this->iterration = $iterration;
}

private function getHash(){
  return $this->hash;
}

private function getSalt(){
  return $this->salt;
}

private function getItteration(){
  return $this->iterration;
}


private function hashing(String $password){

  $hasharray = \unpack("C*", $password);
  for($i = 0; $i < \count($hasharray); $i++ ){

    $this->hash .= chr($hasharray[$i] * $this->secretnum + $this->salt % 128);


  }



}

static function hashing_generate(String $password){

  $salt = (\random_int(1000000000000000000,9000000000000000000));
  $iterration = (\random_int(5,10));

  $hash = new Hash($salt,$iterration);
  $hash->hashing($password);
  return $hash;

}

static function hashing_verify(String $password, Hash $hash){
  $hash->getHash();
  $hash->getSalt();
  $hash->getItteration();

  $newhash = new Hash($hash->getSalt(),$hash->getItteration());
  $newhash->hashing($password);

  return $hash->getHash() === $newhash->getHash();

}

}


?>
