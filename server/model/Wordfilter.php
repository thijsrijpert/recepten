<?php
namespace model;
require_once(dirname(__FILE__,1) . '/Model.php');
require_once(dirname(__FILE__,2) . '/exception/ModelNullException.php');

class Wordfilter extends \model\Model{
  private $word;

  public function __construct(String $word = null){
    $this->word = $word;
  }

  public function getWord() : String{
      if($this->word !== null){
          return $this->word;
      }
      throw new \exception\ModelNullException("The value word is null");
  }

  public function setWord(String $word){
    $this->word = $word;
  }

  public function getVariables(){
      return [['word']];
  }

  public function jsonSerialize() {
    $json_word = '"word" : ' . '"' . $this->word . '"';
    $final_string = "{";

    if($this->word != null){
      $final_string .= $json_word;
    }

    $final_string .= "}";
    return \json_decode($final_string);
  }

}
