require(here)
dat_habitat <- read.csv(here("data","hab.sta.csv"))
par(mfrow = c(3, 1))
elev <- hist(dat_habitat$elev, main = "Histogram of sampling site elevation",xlab="Elevation (m)")
slope <- hist(dat_habitat$slope, main = "Histogram of sampling site slope",xlab="Slope (%) ")
aspect <- hist(dat_habitat$aspect, main = "Histogram of sampling site aspect",xlab="Aspect (degrees)", breaks =4)


line_point_slope = function(x, x1, y1, slope)
{
  get_y_intercept = 
    function(x1, y1, slope) 
      return(-(x1 * slope) + y1)
  
  linear = 
    function(x, yint, slope) 
      return(yint + x * slope)
  
  return(linear(x, get_y_intercept(x1, y1, slope), slope))
}

par(mfrow = c(1, 3))
plot(dat_habitat$elev, dat_habitat$ba.tot, main = "Linear model for elevation", xlab = "Elevation (m)", ylab ="Total basal area (m2/ha)", col=30)
curve(line_point_slope(x, x1 = 85, y1 = 4, slope = 0.125), add = TRUE)
plot(dat_habitat$slope, dat_habitat$ba.tot, main = "Linear model for site slope", xlab = "Slope (%)", ylab ="Total basal area (m2/ha)", col=570)
curve(line_point_slope(x, x1 = 2, y1 = 0, slope = 0.83), add = TRUE)
plot(dat_habitat$aspect, dat_habitat$ba.tot, main = "Linear model for site aspect", xlab = "Aspect (degrees)", ylab ="Total basal area (m2/ha)", col=413)
curve(line_point_slope(x, x1 = 0, y1 = 32, slope = .06), add = TRUE)
