% Crop facts
crop(wheat, cold, temperate, sandy).
crop(barley, cold, temperate, clay).
crop(potato, cold, tropical, sandy).
crop(soybean, cold, temperate, clay).
crop(corn, warm, temperate, sandy).
crop(cotton, warm, temperate, clay).
crop(rice, warm, tropical, clay). 
crop(sugarcane, warm, tropical, loamy).
crop(peanut, warm, tropical, sandy).
crop(tomato, hot, tropical, loamy).

% Get user input
get_input(weather, Weather) :-
   write('Enter weather (warm/cold/hot): '),
   read(Weather).

get_input(region, Region) :-
   write('Enter region (tropical/temperate): '), 
   read(Region).
   
get_input(soil, Soil) :-
   write('Enter soil type (clay/sandy/loamy): '),
   read(Soil).
   
% Suggest crop   
suggest_crop(Crop) :-
   get_input(weather, Weather),
   get_input(region, Region),
   get_input(soil, Soil),
   crop(Crop, Weather, Region, Soil).
   
