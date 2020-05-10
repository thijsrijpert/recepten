<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Review.php');
require_once(dirname(__FILE__, 3) .'\server\model\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\Review.php');
require_once(dirname(__FILE__, 3) . '\server\model\Recipe.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');


final class ReviewDatabaseTest extends TestCase
{

    protected $review;
    protected $mock;
    protected $testobject1;
    protected $testobject2;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->review = new database\Review();

        $this->testobject1 = new model\ReviewPDO(
                                  'Het Beste Gerecht Ooit',
                                  5.0,
                                  'user1',
                                  1,
                                  'Het beste wat ik ooit gegeten heb',
                                  (new \DateTime())->format('Y-m-d'),
                                  1
                              );
        $this->testobject2 = new model\ReviewPDO(
                                  'Slecht',
                                  1.0,
                                  'user2',
                                  1,
                                  'Het smaakte echt niet',
                                  (new \DateTime())->format('Y-m-d'),
                                  3
                              );
    }

    public function testSelect(): void
    {

        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Review WHERE id = :id AND rating = :rating"));
        $this->review = new database\Review($this->mock);
        $input = new \model\Review();
        $input->setId(1);
        $input->setRating(5.0);
        $this->assertEquals(
            array('00000', array(array($this->testobject1))),
            $this->review->select($input)
        );
    }

    public function testSelectOneWhere(): void
    {

        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Review WHERE id = :id"));
        $this->review = new database\Review($this->mock);
        $input = new \model\Review();
        $input->setId(1);

        $this->assertEquals(
            array('00000', array(array($this->testobject1))),
            $this->review->select($input)
        );
    }

    public function testSelectNoWhere(): void
    {
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Review"));
        $this->review = new database\Review($this->mock);

        $this->assertEquals(
            array('00000', array(array($this->testobject1, $this->testobject2))),
            $this->review->select(new \model\Review())
        );
    }
    public function testInsert(): void
    {
        $recipe = new \model\Recipe(1);
        var_dump($recipe);
        $this->assertEquals(
            '00000',
            $this->review->insert(new \model\Review('Dit is een test', 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        );
        $this->assertEquals(
            '23000',
            $this->review->insert(new \model\Review('Dit is een test', 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        );
        $this->assertEquals(
            '22001',
            $this->review->insert(new \model\Review('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG'
          , 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        );
    }


}
