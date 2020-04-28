<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Review.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class ReviewApiTest extends TestCase
{

    protected $review;

    public function setUp() : void{
        $this->review = new api\Review();
    }

    public function testErrorHandlerTitle(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: title', 1, 1);
    }

    public function testErrorHandlerDescription(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: description', 1, 1);
    }

    public function testErrorHandlerRating(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: rating', 1, 1);
    }

    public function testErrorHandlerUsername(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: username', 1, 1);
    }
}
