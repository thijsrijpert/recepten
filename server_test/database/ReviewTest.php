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
    protected $mockUpdate;
    protected $testobject1;
    protected $testobject2;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mockUpdate = $this->createMock('database\QueryBuilderUpdate');
        $this->review = new database\Review();

        $this->testobject1 = new model\ReviewPDO(
                                  'Het Beste Gerecht Ooit',
                                  5.0,
                                  'user1',
                                  1,
                                  'Het beste wat ik ooit gegeten heb',
                                  '2020-05-05',
                                  1
                              );
        $this->testobject2 = new model\ReviewPDO(
                                  'Slecht',
                                  1.0,
                                  'user2',
                                  1,
                                  'Het smaakte echt niet',
                                  '2020-05-05',
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

    public function testUpdateOneWhere(): void
    {
      $this->mockUpdate->expects($this->any())->method('getSql')->will($this->returnValue("UPDATE Review SET title = :titleUpdate WHERE id = :id"));
      $this->timeOfDay = new database\Review($this->mockUpdate);
      $model = new \model\Review();
      $model->setId(3);
        $this->assertEquals(
            '00000',
            $this->timeOfDay->update(new \model\Review('Best Redelijk'), $model)
        );
    }

    public function testUpdate(): void
    {
      $this->mockUpdate->expects($this->any())->method('getSql')->will($this->returnValue("UPDATE Review SET rating = :ratingUpdate WHERE title = :title AND description = :description AND rating = :rating AND review_date = :review_date AND recipe_id = :recipe_id"));
      $this->timeOfDay = new database\Review($this->mockUpdate);
      var_dump(DateTime::createFromFormat('d-m-Y', '05-05-2020'));
      $model = new \model\Review();
      $model->setRating(3);
        $this->assertEquals(
            '00000',
            $this->timeOfDay->update($model, new \model\Review('Slecht', 1, null, new \model\Recipe(1), 'Het smaakte echt niet', DateTime::createFromFormat('d-m-Y', '05-05-2020')))
        );
    }


    public function testInsert(): void
    {
        $recipe = new \model\Recipe(1);

        $this->assertEquals(
            '00000',
            $this->review->insert(new \model\Review('Dit is een test', 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        );
        $this->assertEquals(
            '23000',
            $this->review->insert(new \model\Review('Dit is een test', 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        );
        // $this->assertEquals(
        //     '22001',
        //     $this->review->insert(new \model\Review('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG'
        //   , 3.5, new \model\User('test'), $recipe, 'dit is een test'))
        // );
    }


}
