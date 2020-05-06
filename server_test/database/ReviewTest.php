<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Review.php');
require_once(dirname(__FILE__, 3) .'\server\model\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\Review.php');
require_once(dirname(__FILE__, 3) . '\server\model\ReceptModel.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');


final class ReviewDatabaseTest extends TestCase
{

    protected $review;

    public function setUp() : void{
        $this->review = new database\Review();
    }

    public function testInsert(): void
    {
        $recipe = new \model\Recipe();
        $recipe->setId(1);
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
