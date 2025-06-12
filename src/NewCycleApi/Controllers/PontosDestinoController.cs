using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NewCycleApi.Data;
using NewCycleApi.Models;
using NewCycleApi.ViewModels;

namespace NewCycleApi.Controllers;

[ApiController]
[Route("api/[controller]")]
public class PontosDestinoController : ControllerBase
{
    private readonly AppDbContext _context;

    public PontosDestinoController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<PontoDestinoViewModel>>> GetAll([FromQuery]int page = 1, [FromQuery]int pageSize = 10)
    {
        var skip = (page - 1) * pageSize;
        var itens = await _context.PontosDestino
            .AsNoTracking()
            .Skip(skip)
            .Take(pageSize)
            .Select(r => new PontoDestinoViewModel(r.Id, r.Nome))
            .ToListAsync();
        return Ok(itens);
    }
}
