(function() {
  var PixelPlot;

  if (!$.fn.plot) {
    throw new Error('jquery.flot.js required');
  }


  /*
   * @class PixelPlot
   */

  PixelPlot = function($graph, graph_data, plot_options, options) {
    var available_colors, d, data, dataItem, previousPoint, timer, _i, _j, _len, _len1;
    if (options == null) {
      options = {};
    }
    this.options = $.extend(true, {}, PixelPlot.DEFAULTS, options || {});
    this.plot_options = $.extend(true, {}, PixelPlot.PLOT_DEFAULTS, plot_options || {});
    data = [];
    this.current_width = null;
    this.plot_obj = null;
    timer = null;
    available_colors = window.PixelAdmin.settings.consts.COLORS.slice(0);
    for (_i = 0, _len = graph_data.length; _i < _len; _i++) {
      d = graph_data[_i];
      data.push($.extend({}, d));
    }
    this.$graph = $graph.addClass('pa-flot-graph');
    this.$graph_container = $('<div class="pa-flot-container"></div>');
    this.$graph_info = $('<div class="pa-flot-info"></div>');
    this.$graph_container.insertAfter(this.$graph).append(this.$graph_info).append(this.$graph);
    this.resizeContainer();
    if (!data.length) {
      return;
    }
    for (_j = 0, _len1 = data.length; _j < _len1; _j++) {
      dataItem = data[_j];
      if (dataItem.color === void 0) {
        dataItem.color = available_colors.shift();
      }
      if (dataItem.filledPoints === true) {
        $.extend(true, dataItem, {
          points: {
            radius: this.options.pointRadius,
            fillColor: dataItem.color
          }
        });
        delete dataItem['filledPoints'];
      }
      this.$graph_info.append($('<span><i style="background: ' + dataItem.color + '"></i>' + dataItem.label + '</span>'));
    }
    this.plot_obj = $.plot($graph, data, this.plot_options);
    if (this.plot_options.series.pie === void 0) {
      previousPoint = null;
      $graph.bind('plothover', (function(_this) {
        return function(event, pos, item) {
          var x, y;
          if (item) {
            if (previousPoint !== item.dataIndex) {
              previousPoint = item.dataIndex;
              $('.pa-flot-tooltip').remove();
              x = item.datapoint[0];
              y = item.datapoint[1];
              return _this.showTooltip(item.pageX, item.pageY, eval(_this.options.tooltipText));
            }
          } else {
            $('.pa-flot-tooltip').remove();
            return previousPoint = null;
          }
        };
      })(this));
    }
    return $(window).on('pa.resize', $.proxy(this.resizeContainer, this));
  };

  PixelPlot.prototype.resizeContainer = function() {
    var height, width;
    width = this.$graph_container.innerWidth();
    if (width === this.current_width) {
      return;
    }
    height = this.options.height === null ? Math.ceil(width * this.options.heightRatio) : this.options.height;
    this.$graph.css({
      width: width,
      height: height
    });
    this.current_width = width;
    if (this.plot_obj) {
      this.plot_obj.getPlaceholder().css({
        width: width,
        height: height
      });
      this.plot_obj.resize();
      this.plot_obj.setupGrid();
      return this.plot_obj.draw();
    }
  };

  PixelPlot.prototype.showTooltip = function(x, y, contents) {
    var tooltip;
    tooltip = $('<div class="pa-flot-tooltip">' + contents + '</div>').appendTo('body');
    if ((x + 20 + tooltip.width()) > (this.$graph_container.offset().left + this.$graph_container.width())) {
      x -= 40 + tooltip.width();
    } else {
      x += 20;
    }
    return tooltip.css({
      top: y - 16,
      left: x
    }).fadeIn();
  };

  PixelPlot.DEFAULTS = {
    pointRadius: 4,
    height: null,
    heightRatio: 0.5,
    tooltipText: 'x - y'
  };

  PixelPlot.PLOT_DEFAULTS = {
    series: {
      shadowSize: 0
    },
    grid: {
      color: '#999',
      borderColor: '#fff',
      borderWidth: 1,
      hoverable: true
    },
    xaxis: {
      tickColor: '#fff'
    },
    legend: {
      show: false
    }
  };

  $.fn.pixelPlot = function(graph_data, plot_options, options) {
    return this.each(function() {
      var pflot;
      pflot = $.data(this, 'PixelPlot');
      if (!pflot) {
        return $.data(this, 'PixelPlot', new PixelPlot($(this), graph_data, plot_options, options));
      } else {
        if (plot_options === 'resizeContainer') {
          return pflot.resizeContainer();
        }
      }
    });
  };

  $.fn.pixelPlot.Constructor = PixelPlot;

}).call(this);
;
(function() {
  var $Vague, blurOff, blurOn, modal_hide, modal_show, _blurred;

  if (!$.fn.modal || !$.fn.Vague || !$('html').hasClass('not-ie')) {
    return;
  }

  modal_show = $.fn.modal.Constructor.prototype.show;

  modal_hide = $.fn.modal.Constructor.prototype.hide;

  $Vague = null;

  _blurred = false;

  blurOn = function() {
    if (_blurred) {
      return;
    }
    if (!$Vague) {
      $Vague = $('#main-wrapper').Vague({
        intensity: 3,
        forceSVGUrl: false
      });
    }
    $Vague.blur();
    return _blurred = true;
  };

  blurOff = function() {
    if (!_blurred) {
      return;
    }
    if ($Vague) {
      $Vague.unblur();
    }
    return _blurred = false;
  };

  $.fn.modal.Constructor.prototype.show = function() {
    modal_show.call(this);
    if (this.$element.hasClass('modal-blur')) {
      $('body').append(this.$element);
      if (getScreenSize($('#small-screen-width-point'), $('#tablet-screen-width-point')) === 'desktop') {
        blurOn();
      }
      return $(window).on('pa.resize.modal_blur', function() {
        if (getScreenSize($('#small-screen-width-point'), $('#tablet-screen-width-point')) === 'desktop') {
          return blurOn();
        } else {
          return blurOff();
        }
      });
    } else {
      return blurOff();
    }
  };

  $.fn.modal.Constructor.prototype.hide = function() {
    modal_hide.call(this);
    blurOff();
    return $(window).off('pa.resize.modal_blur').on('hidden', '.modal', function() {
      return alert('asd');
    });
  };

}).call(this);
;
/*(function() {
  var datepicker;

  if (!$.fn.datepicker) {
    throw new Error('bootstrap-datepicker.js required');
  }

  datepicker = $.fn.datepicker;

  $.fn.datepicker = function(options) {
    options = $.extend({
      rtl: $('body').hasClass('right-to-left')
    }, options || {});
    return datepicker.call($(this), options);
  };

}).call(this);
;
(function() {
  var timepicker_init;

  if (!$.fn.timepicker) {
    throw new Error('bootstrap-timepicker.js required');
  }

  timepicker_init = $.fn.timepicker.Constructor.prototype._init;

  $.fn.timepicker.Constructor.prototype._init = function() {
    if (this.$element.parent().hasClass('input-group')) {
      this.$element.parent('.input-group').find('.input-group-addon').on('click.timepicker', $.proxy(this.showWidget, this));
      this.$element.on({
        'focus.timepicker': $.proxy(this.highlightUnit, this),
        'click.timepicker': $.proxy(this.highlightUnit, this),
        'keydown.timepicker': $.proxy(this.elementKeydown, this),
        'blur.timepicker': $.proxy(this.blurElement, this)
      });
    }
    return timepicker_init.call(this);
  };

  $.fn.timepicker.Constructor.prototype.getTemplate = function() {
    var hourTemplate, meridianTemplate, minuteTemplate, secondTemplate, template, templateContent;
    if (this.showInputs) {
      hourTemplate = '<input type="text" name="hour" class="bootstrap-timepicker-hour form-control" maxlength="2"/>';
      minuteTemplate = '<input type="text" name="minute" class="bootstrap-timepicker-minute form-control" maxlength="2"/>';
      secondTemplate = '<input type="text" name="second" class="bootstrap-timepicker-second form-control" maxlength="2"/>';
      meridianTemplate = '<input type="text" name="meridian" class="bootstrap-timepicker-meridian form-control" maxlength="2"/>';
    } else {
      hourTemplate = '<span class="bootstrap-timepicker-hour"></span>';
      minuteTemplate = '<span class="bootstrap-timepicker-minute"></span>';
      secondTemplate = '<span class="bootstrap-timepicker-second"></span>';
      meridianTemplate = '<span class="bootstrap-timepicker-meridian"></span>';
    }
    templateContent = '<table>';
    templateContent += '<tr>';
    templateContent += '<td><a href="#" data-action="incrementHour" class="timepicker-increment"><i class="fa fa-chevron-up"></i></a></td>';
    templateContent += '<td class="separator">&nbsp;</td>';
    templateContent += '<td><a href="#" data-action="incrementMinute" class="timepicker-increment"><i class="fa fa-chevron-up"></i></a></td>';
    if (this.showSeconds) {
      templateContent += '<td class="separator">&nbsp;</td><td><a href="#" data-action="incrementSecond" class="timepicker-increment"><i class="fa fa-chevron-up"></i></a></td>';
    }
    if (this.showMeridian) {
      templateContent += '<td class="separator">&nbsp;</td><td class="meridian-column"><a href="#" data-action="toggleMeridian" class="timepicker-increment"><i class="fa fa-chevron-up"></i></a></td>';
    }
    templateContent += '</tr>';
    templateContent += '<tr>';
    templateContent += '<td>' + hourTemplate + '</td> ';
    templateContent += '<td class="separator">:</td>';
    templateContent += '<td>' + minuteTemplate + '</td> ';
    if (this.showSeconds) {
      templateContent += '<td class="separator">:</td><td>' + secondTemplate + '</td>';
    }
    if (this.showMeridian) {
      templateContent += '<td class="separator">&nbsp;</td><td>' + meridianTemplate + '</td>';
    }
    templateContent += '</tr>';
    templateContent += '<tr>';
    templateContent += '<td><a href="#" data-action="decrementHour" class="timepicker-decrement"><i class="fa fa-chevron-down"></i></a></td>';
    templateContent += '<td class="separator"></td>';
    templateContent += '<td><a href="#" data-action="decrementMinute" class="timepicker-decrement"><i class="fa fa-chevron-down"></i></a></td>';
    if (this.showSeconds) {
      templateContent += '<td class="separator">&nbsp;</td><td><a href="#" data-action="decrementSecond" class="timepicker-decrement"><i class="fa fa-chevron-down"></i></a></td>';
    }
    if (this.showMeridian) {
      templateContent += '<td class="separator">&nbsp;</td><td><a href="#" data-action="toggleMeridian" class="timepicker-decrement"><i class="fa fa-chevron-down"></i></a></td>';
    }
    templateContent += '</tr>';
    templateContent += '</table>';
    if (this.template === 'modal') {
      template = '<div class="bootstrap-timepicker-widget modal fade" tabindex="-1" role="dialog" style="display: none;">';
      template += '<div class="modal-dialog modal-sm">';
      template += '<div class="modal-content">';
      template += '<div class="modal-header">';
      template += '<button type="button" class="close" data-dismiss="modal">×</button>';
      template += '<h4>Pick a Time</h4>';
      template += '</div>';
      template += '<div class="modal-body">' + templateContent + '</div>';
      template += '<div class="modal-footer">';
      template += '<buttom class="btn btn-primary" data-dismiss="modal">OK</buttom>';
      template += '</div>';
      template += '</div>';
      template += '</div>';
      template += '</div>';
    } else if (this.template === 'dropdown') {
      template = '<div class="bootstrap-timepicker-widget dropdown-menu">' + templateContent + '</div>';
    }
    return template;
  };

}).call(this);
;
(function() {
  var datepaginator;

  if (!$.fn.datepaginator) {
    throw new Error('bootstrap-datepaginator.js required');
  }

  datepaginator = $.fn.datepaginator;

  $.fn.datepaginator = function(options, args) {
    return datepaginator.call(this, $.extend({}, {
      injectStyle: false,
      itemWidth: 45,
      selectedItemWidth: 160
    }, options || {}), args);
  };

}).call(this);
;
(function() {
  var tabdrop;

  if (!$.fn.tabdrop) {
    throw new Error('bootstrap-tabdrop.js required');
  }

  tabdrop = $.fn.tabdrop;

  $.fn.tabdrop = function(options) {
    options = $.extend({}, $.fn.tabdrop.defaults, options);
    return this.each(function() {
      var $this, data;
      $this = $(this);
      tabdrop.call($this, options);
      data = $this.data('tabdrop');
      if (data) {
        data.dropdown.on("click", "li", function() {
          $(this).parent().parent().find("a.dropdown-toggle").empty().html('<span class="display-tab"> ' + $(this).text() + ' </span><b class="caret"></b>');
          return data.layout();
        });
        return data.element.on('click', '> li', function() {
          if ($(this).hasClass('tabdrop')) {
            return;
          }
          data.element.find("> .tabdrop > a.dropdown-toggle").empty().html(options.text + ' <b class="caret"></b>');
          return data.layout();
        });
      }
    });
  };

  $.fn.tabdrop.defaults = {
    text: '<i class="fa fa-bars"></i>'
  };

}).call(this);
;
(function() {
  if (!$.validator) {
    throw new Error('jquery.validate.js required');
  }

  $.validator.setDefaults({
    highlight: function(element) {
      return $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function(element) {
      return $(element).closest('.form-group').removeClass('has-error').find('help-block-hidden').removeClass('help-block-hidden').addClass('help-block').show();
    },
    errorElement: 'div',
    errorClass: 'jquery-validate-error',
    errorPlacement: function(error, element) {
      var $p, has_e, is_c;
      is_c = element.is('input[type="checkbox"]') || element.is('input[type="radio"]');
      has_e = element.closest('.form-group').find('.jquery-validate-error').length;
      if (!is_c || !has_e) {
        if (!has_e) {
          element.closest('.form-group').find('.help-block').removeClass('help-block').addClass('help-block-hidden').hide();
        }
        error.addClass('help-block');
        if (is_c) {
          return element.closest('[class*="col-"]').append(error);
        } else {
          $p = element.parent();
          if ($p.is('.input-group')) {
            return $p.parent().append(error);
          } else {
            return $p.append(error);
          }
        }
      }
    }
  });

}).call(this);
;
(function() {
  var knob;

  if (!$.fn.knob) {
    throw new Error('jquery.knob.js required');
  }

  knob = $.fn.knob;

  $.fn.knob = function(o) {
    var $body;
    $body = $('body');
    return knob.call(this, o).each(function() {
      var $input;
      if ($body.hasClass('right-to-left')) {
        $input = $(this).find('input');
        return $input.css({
          'margin-left': 0,
          'margin-right': $input.css('margin-left')
        });
      }
    });
  };

}).call(this);
;*/
(function() {
  var getBarWidth;

  if (!$.fn.sparkline) {
    throw new Error('jquery.sparkline.js required');
  }

  getBarWidth = function($el, count, space) {
    var s, w;
    w = $el.outerWidth();
    s = parseInt(space) * (count - 1);
    return Math.floor((w - s) / count);
  };

  $.fn.pixelSparkline = function() {
    var bars_space, f_args, is_bars, vals_count;
    f_args = arguments;
    is_bars = false;
    vals_count = 0;
    bars_space = '2px';
    if (f_args[0] instanceof Array && f_args[1] instanceof Object && f_args[1].type === 'bar' && f_args[1].width === '100%') {
      is_bars = true;
      vals_count = f_args[0].length;
      if (f_args[1].barSpacing) {
        bars_space = f_args[1].barSpacing;
      }
      f_args[1].barWidth = getBarWidth($(this), vals_count, bars_space);
    }
    $.fn.sparkline.apply(this, f_args);
    return $(window).on('pa.resize', (function(_this) {
      return function() {
        if (is_bars) {
          f_args[1].barWidth = getBarWidth($(_this), vals_count, bars_space);
        }
        return $.fn.sparkline.apply(_this, f_args);
      };
    })(this));
  };

}).call(this);