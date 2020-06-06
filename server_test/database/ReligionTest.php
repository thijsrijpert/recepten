<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Review.php');
require_once(dirname(__FILE__, 3) .'\server\model\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\Review.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');


final class ReligionDatabaseTest extends TestCase
{

    protected $religion;
    protected $mockUpdate;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mockUpdate = $this->createMock('database\QueryBuilderUpdate');
        $this->religion = new database\Religion();
    }

    public function testSelect(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion WHERE name = :name AND id > :id"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\Religion('Christendom', 1)))),
            $this->religion->select(new \model\Religion('Christendom', 0))
        );
    }

    public function testSelectOneWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion WHERE name = :name"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\Religion('Christendom', 1)))),
            $this->religion->select(new \model\Religion('Christendom'))
        );
    }

    public function testSelectNoWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Religion"));
      $this->religion = new database\Religion($this->mock);
        $this->assertEquals(
            array('00000', array(array(new model\Religion('Christendom', 1), new model\Religion('Grote Spaghettimonster', 5), new model\Religion('Hinduïsme', 2),new model\Religion('Islam', 3), new model\Religion('Jodendom', 4), new model\Religion('Satanisme', 666)))),
            $this->religion->select(new \model\Religion())
        );
    }

    public function testUpdate(){
      $this->mockUpdate->expects($this->any())->method('getSql')->will($this->returnValue("UPDATE Religion SET name = :nameUpdate WHERE id = :id"));
      $this->religion = new database\Religion($this->mockUpdate);
        $this->assertEquals(
            '00000',
            $this->religion->update(new \model\Religion('Kleine Spagettiemonster'), new \model\Religion(null,5))
        );
    }

    public function testInsert(): void
    {
        $this->assertEquals(
            '00000',
            $this->religion->insert(new \model\Religion('Boedisme'))
        );
        $this->assertEquals(
            '23000',
            $this->religion->insert(new \model\Religion('Boedisme'))
        );
        // $this->assertEquals(
        //     '22001',
        //     $this->religion->insert(new \model\Religion('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG'))
        // );
    }
}
